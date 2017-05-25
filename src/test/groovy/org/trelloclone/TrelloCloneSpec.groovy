package org.trelloclone

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
public class TrelloCloneSpec extends Specification {
    @AutoCleanup
    @Shared
    GroovyRatpackMainApplicationUnderTest sut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient httpClient = sut.httpClient

    def setupSpec() {
    }

    def cleanupSpec() {
    }

    def "get boards"() {
        when:
        get('api/v1/boards')

        then:
        response.statusCode == 200
    }
}