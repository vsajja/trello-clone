package org.trelloclone

import groovy.json.JsonOutput
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

    static final String TEST_USER_VSAJJA_USERNAME = 'test_user_vsajja'
    static final String TEST_USER_VSAJJA_PASSWORD = 'test_user_vsajja_password'

    @Shared
    User vsajja

    def setupSpec() {
    }

    def cleanupSpec() {
        context.deleteFrom(USER).where(USER.USERNAME.equal(TEST_USER_VSAJJA_USERNAME)).execute()
    }

    def "register vsajja"() {
        setup:
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: TEST_USER_VSAJJA_USERNAME,
                     password: TEST_USER_VSAJJA_PASSWORD])
            )
        }

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
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: TEST_USER_VSAJJA_USERNAME,
                     password: TEST_USER_VSAJJA_PASSWORD])
            )
        }

        when:
        post('api/v1/register')

        then:
        response.statusCode == HttpResponseStatus.CONFLICT.code()
        userDao.count() == old(userDao.count())
    }

    def "register vsajja with invalid params (bad request)"() {
        setup:
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: TEST_USER_VSAJJA_USERNAME])
            )
        }

        when:
        post('api/v1/register')

        then:
        response.statusCode == HttpResponseStatus.BAD_REQUEST.code()
        userDao.count() == old(userDao.count())
    }

    def "login vsajja"() {
        setup:
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: TEST_USER_VSAJJA_USERNAME,
                     password: TEST_USER_VSAJJA_PASSWORD])
            )
        }

        when:
        post('api/v1/login')

        then:
        response.statusCode == HttpResponseStatus.OK.code()
    }

    def "login vsajja with invalid credentials (unauthorized)"() {
        setup:
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: TEST_USER_VSAJJA_USERNAME,
                     password: 'wrong password'])
            )
        }

        when:
        post('api/v1/login')

        then:
        response.statusCode == HttpResponseStatus.UNAUTHORIZED.code()
    }

    def "login vsajja with invalid params (bad reqeust)"() {
        setup:
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: TEST_USER_VSAJJA_USERNAME])
            )
        }

        when:
        post('api/v1/login')

        then:
        response.statusCode == HttpResponseStatus.BAD_REQUEST.code()
    }
}