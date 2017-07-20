package org.trelloclone

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.netty.handler.codec.http.HttpResponseStatus
import jooq.generated.tables.pojos.Board
import jooq.generated.tables.pojos.User
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.http.client.RequestSpec
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll

import static jooq.generated.Tables.BOARD
import static jooq.generated.Tables.USER;

@Stepwise
public class BoardSpec extends TrelloCloneSpec {
    @AutoCleanup
    @Shared
    GroovyRatpackMainApplicationUnderTest sut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient httpClient = sut.httpClient

    static final String TEST_USER_VSAJJA_USERNAME = 'BoardSpec_test_user_vsajja'
    static final String TEST_BOARD_WELCOME_NAME = 'BoardSpec_WelcomeBoard'

    @Shared
    User vsajja

    @Shared
    Board welcomeBoard

    def setupSpec() {
        vsajja = new User(null, TEST_USER_VSAJJA_USERNAME, null)
        userDao.insert(vsajja)
        vsajja = userDao.fetchByUsername(TEST_USER_VSAJJA_USERNAME)
    }

    def cleanupSpec() {
        // delete all test boards and users
        context.deleteFrom(BOARD).where(BOARD.NAME.equal(TEST_BOARD_WELCOME_NAME)).execute()
        context.deleteFrom(BOARD).where(BOARD.USER_ID.eq(vsajja.userId)).execute()
        context.deleteFrom(USER).where(USER.USERNAME.equal(TEST_USER_VSAJJA_USERNAME)).execute()
    }

    def "user created"() {
        expect:
        vsajja
        vsajja.userId
    }

    def "user should have no boards"() {
        when:
        get("api/v1/users/${vsajja.userId}/boards")

        then:
        response.statusCode == HttpResponseStatus.OK.code()
        def userBoards = new JsonSlurper().parseText(response.body.text)
        userBoards.collect().size() == 0
    }

    def "create a welcome board"() {
        setup:
        createBoardRequestSpec(TEST_BOARD_WELCOME_NAME)

        when:
        post("api/v1/users/${vsajja.userId}/boards")

        then:
        response.statusCode == HttpResponseStatus.OK.code()
    }

    def "user should have a welcome board"() {
        when:
        get("api/v1/users/${vsajja.userId}/boards")

        then:
        response.statusCode == HttpResponseStatus.OK.code()
        def userBoards = new JsonSlurper().parseText(response.body.text)
        userBoards.collect().size() == 1
        userBoards.collect { it.name }.contains(TEST_BOARD_WELCOME_NAME)
    }

    def "get welcome board by id"() {
        setup:
        welcomeBoard = getBoard(TEST_BOARD_WELCOME_NAME)

        when:
        get("api/v1/boards/${welcomeBoard.boardId}")

        then:
        response.statusCode == HttpResponseStatus.OK.code()
        def result = new JsonSlurper().parseText(response.body.text)
        result.name == TEST_BOARD_WELCOME_NAME
    }

    def "get a board that doesn't exist (not found)"() {
        when:
        delete("api/v1/boards/-1")

        then:
        response.statusCode == HttpResponseStatus.NOT_FOUND.code()
    }

    def "delete welcome board by id"() {
        when:
        delete("api/v1/boards/${welcomeBoard.boardId}")

        then:
        response.statusCode == HttpResponseStatus.OK.code()
    }

    def "delete a board that doesn't exist (not found)"() {
        when:
        delete("api/v1/boards/-1")

        then:
        response.statusCode == HttpResponseStatus.NOT_FOUND.code()
    }

    def "create an invalid board (bad request)"() {
        setup:
        createBoardRequestSpec(null)

        when:
        post("api/v1/users/${vsajja.userId}/boards")

        then:
        response.statusCode == HttpResponseStatus.BAD_REQUEST.code()
    }

    @Unroll
    def "vsajja creates #boardName"() {
        setup:
        createBoardRequestSpec(boardName)

        when:
        post("api/v1/users/${vsajja.userId}/boards")

        then:
        response.statusCode == HttpResponseStatus.OK.code()

        where:
        boardName << ['BoardA', 'BoardB', 'BoardC']
    }

    @Unroll
    def "vsajja should have #boardName"() {
        when:
        get("api/v1/users/${vsajja.userId}/boards")

        then:
        response.statusCode == HttpResponseStatus.OK.code()
        println response.body.text
        def userBoards = new JsonSlurper().parseText(response.body.text)
        userBoards.collect { it.name }.contains(boardName)

        where:
        boardName << ['BoardA', 'BoardB', 'BoardC']
    }

    def getBoard(String name) {
        context.selectFrom(BOARD).where(BOARD.NAME.equal(name)).fetchInto(Board.class)
    }
}