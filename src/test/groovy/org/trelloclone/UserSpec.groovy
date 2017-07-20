package org.trelloclone

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.netty.handler.codec.http.HttpResponseStatus
import jooq.generated.tables.pojos.User
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.http.client.RequestSpec
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Stepwise

import static jooq.generated.Tables.*;

@Stepwise
public class UserSpec extends TrelloCloneSpec {
    @AutoCleanup
    @Shared
    GroovyRatpackMainApplicationUnderTest sut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient httpClient = sut.httpClient

    static final String TEST_USER_VSAJJA_USERNAME = this.getClass().getSimpleName() +  '_test_user_vsajja'
    static final String TEST_USER_VSAJJA_PASSWORD = this.getClass().getSimpleName() +  '_test_user_vsajja_password'

    @Shared
    User vsajja

    def setupSpec() {
    }

    def cleanupSpec() {
        context.deleteFrom(USER).where(USER.USERNAME.equal(TEST_USER_VSAJJA_USERNAME)).execute()
    }

    def "register vsajja"() {
        setup:
        createRequestSpec(TEST_USER_VSAJJA_USERNAME, TEST_USER_VSAJJA_PASSWORD)

        when:
        post('api/v1/register')

        then:
        response.statusCode == HttpResponseStatus.OK.code()
        userDao.count() == old(userDao.count()) + 1
    }

    def "get vsajja"() {
        setup:
        vsajja = userDao.fetchByUsername(TEST_USER_VSAJJA_USERNAME)

        expect:
        vsajja
        vsajja.userId
    }

    def "register vsajja again (conflict)"() {
        setup:
        createRequestSpec(TEST_USER_VSAJJA_USERNAME, TEST_USER_VSAJJA_PASSWORD)

        when:
        post('api/v1/register')

        then:
        response.statusCode == HttpResponseStatus.CONFLICT.code()
        userDao.count() == old(userDao.count())
    }

    def "register vsajja with invalid params (bad request)"() {
        setup:
        createRequestSpec(TEST_USER_VSAJJA_USERNAME, null)

        when:
        post('api/v1/register')

        then:
        response.statusCode == HttpResponseStatus.BAD_REQUEST.code()
        userDao.count() == old(userDao.count())
    }

    def "login vsajja"() {
        setup:
        createRequestSpec(TEST_USER_VSAJJA_USERNAME, TEST_USER_VSAJJA_PASSWORD)

        when:
        post('api/v1/login')

        then:
        response.statusCode == HttpResponseStatus.OK.code()
    }

    def "login vsajja with invalid credentials (unauthorized)"() {
        setup:
        createRequestSpec(TEST_USER_VSAJJA_USERNAME, 'wrong password')

        when:
        post('api/v1/login')

        then:
        response.statusCode == HttpResponseStatus.UNAUTHORIZED.code()
    }

    def "login vsajja with invalid params (bad reqeust)"() {
        setup:
        createRequestSpec(TEST_USER_VSAJJA_USERNAME, null)

        when:
        post('api/v1/login')

        then:
        response.statusCode == HttpResponseStatus.BAD_REQUEST.code()
    }

    def "get users"() {
        when:
        get('api/v1/users')

        then:
        response.statusCode == HttpResponseStatus.OK.code()
        def users = new JsonSlurper().parseText(response.body.text)
        users.collect { it.username }.contains(TEST_USER_VSAJJA_USERNAME)
    }

    /**
     * Creates a request spec for the register and login endpoints
     * @param username
     * @param password
     * @return
     */
    def createRequestSpec(String username, String password) {
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: username,
                     password: password])
            )
        }
    }
}