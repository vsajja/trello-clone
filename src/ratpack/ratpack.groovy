import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import jooq.generated.tables.TeamBoard
import jooq.generated.tables.pojos.Board
import jooq.generated.tables.pojos.BoardList
import jooq.generated.tables.pojos.Card
import jooq.generated.tables.pojos.Team
import jooq.generated.tables.pojos.TeamMember
import jooq.generated.tables.pojos.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.trelloclone.TrelloCloneService
import org.trelloclone.exceptions.InvalidCredentialsException
import org.trelloclone.postgres.PostgresConfig
import org.trelloclone.postgres.PostgresModule
import ratpack.groovy.sql.SqlModule
import ratpack.handling.RequestLogger
import ratpack.hikari.HikariModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    serverConfig {
        props("db.properties")
        require("/postgres", PostgresConfig)
    }

    bindings {
        module HikariModule, { HikariConfig config ->
            config.setMaximumPoolSize(5)
            config.dataSource = new PostgresModule().dataSource(serverConfig.get('/postgres', PostgresConfig))
        }
        module SqlModule
        bind TrelloCloneService
    }

    handlers { TrelloCloneService trelloCloneService ->
        all RequestLogger.ncsa(log)

        prefix('api/v1') {
            all {
                response.headers.add('Access-Control-Allow-Origin', '*')
                response.headers.add('Access-Control-Allow-Headers', 'Authorization, origin, x-requested-with, content-type')
                response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
                next()
            }

            post('register') {
                parse(jsonNode()).map { params ->
                    def username = params.get('username')?.textValue()
                    def password = params.get('password')?.textValue()

                    if(!username || !password) {
                        clientError(400)
                    }

                    trelloCloneService.registerUser(username, password)
                }.onError { Throwable e ->
                    if(e.message.contains('unique constraint')) {
                        clientError(409)
                    }
                    throw e
                }.then { User user ->
                    render json(user)
                }
            }

            post('login') {
                parse(jsonNode()).map { params ->
                    def username = params.get('username')?.textValue()
                    def password = params.get('password')?.textValue()

                    if(!username || !password) {
                        clientError(400)
                    }

                    trelloCloneService.login(username, password)
                }.onError { Throwable e ->
                    if(e instanceof InvalidCredentialsException) {
                        clientError(401)
                    }
                    throw e
                }.then { User user ->
                    render json(user)
                }
            }

            path('users') {
                byMethod {
                    get {
                        def users = trelloCloneService.getUsers()
                        render json(users)
                    }
                }
            }

            path('users/:userId/boards') {
                def userId = pathTokens['userId']

                byMethod {
                    get {
                        def boards = trelloCloneService.getBoards(userId)
                        render json(boards)
                    }

                    post {
                        parse(jsonNode()).map { params ->
                            def name = params.get('name')?.textValue()

                            if(!name) {
                                clientError(400)
                            }

                            trelloCloneService.createBoard(userId, name)
                        }.onError { Throwable e ->
                            if(e.message.contains('unique constraint')) {
                                clientError(409)
                            }
                        }.then { Board board ->
                            render json(board)
                        }
                    }
                }
            }

            path('boards/:boardId') {
                def boardId = pathTokens['boardId']
                byMethod {
                    get {
                        def board = trelloCloneService.getBoard(boardId)
                        if(board) {
                            render json(board)
                        }
                        else {
                            clientError(404)
                        }
                    }

                    delete {
                        int result = trelloCloneService.deleteBoard(boardId)
                        if(result > 0) {
                            response.send()
                        }
                        else {
                            clientError(404)
                        }
                    }
                }
            }

            path('users/:userId/teams') {
                def userId = pathTokens['userId']
                byMethod {
                    get {
                        def teams = trelloCloneService.getTeams(userId)
                        render json(teams)
                    }

                    post {
                        parse(jsonNode()).map { params ->
                            def name = params.get('name')?.textValue()
                            def description = params.get('description')?.textValue()
                            assert name

                            trelloCloneService.createTeam(userId, name, description)
                        }.onError { Throwable e ->
                            if(e.message.contains('unique constraint')) {
                                clientError(409)
                            }
                            throw e
                        }.then { Team team ->
                            render json(team)
                        }
                    }
                }
            }

            path('teams/:teamId/members') {
                def teamId = pathTokens['teamId']
                byMethod {
                    get {
                        def teamMembers = trelloCloneService.getTeamMembers(teamId)
                        render json(teamMembers)
                    }

                    post {
                        parse(jsonNode()).map { params ->
                            log.info(params.toString())

                            def userId = (String) params.get('userId')?.intValue()
                            def username = params.get('username')?.textValue()

                            assert userId
                            assert username

                            trelloCloneService.addTeamMember(teamId, userId)
                        }.onError { Throwable e ->
                            if(e.message.contains('unique constraint')) {
                                clientError(409)
                            }
                            throw e
                        }.then { TeamMember teamMember ->
                            render json(teamMember)
                        }
                    }
                }
            }

            path('teams/members/:teamMemberId') {
                def teamMemberId = pathTokens['teamMemberId']
                byMethod {
                    delete {
                        log.info('delete' + teamMemberId)
                        trelloCloneService.removeTeamMember(teamMemberId)
                        render 'delete'
                    }
                }
            }

            path('teams/:teamId/boards') {
                def teamId = pathTokens['teamId']
                byMethod {
                    post {
                        parse(jsonNode()).map { params ->
                            def name = params.get('name')?.textValue()

                            assert name

                            trelloCloneService.createTeamBoard(teamId, name)
                        }.onError { Throwable e ->
                            if(e.message.contains('unique constraint')) {
                                clientError(409)
                            }
                            throw e
                        }.then { TeamBoard teamBoard ->
                            render json(teamBoard)
                        }
                    }
                }
            }

            path('boards/:boardId/lists') {
                def boardId = pathTokens['boardId']
                byMethod {
                    get {
                        def result = trelloCloneService.getBoardLists(boardId)
                        render json(result)
                    }

                    post {
                        parse(jsonNode()).map { params ->
                            def name = params.get('name')?.textValue()
                            assert name

                            trelloCloneService.createBoardList(boardId, name)
                        }.then { BoardList boardList ->
                            render json(boardList)
                        }
                    }

                    put {
                        parse(jsonNode()).map { params ->
                            def boardLists = new JsonSlurper().parseText(params.toString())

                            log.info(new JsonBuilder(boardLists).toPrettyString())

                            def lists = (Map) boardLists

                            List<Card> cardsToUpdate = new ArrayList<Card>()

                            lists.each { id, list ->
                                Integer listId = Integer.parseInt(id)

                                list.cards.each { card ->
                                    Card cardToUpdate = new Card(card.cardId, card.name, card.description, listId)
                                    cardsToUpdate.add(cardToUpdate)
                                }
                            }

                            log.info(cardsToUpdate.toListString())

                            trelloCloneService.updateCards(cardsToUpdate)
                        }.then {
                            response.send()
                        }
                    }
                }
            }

            path('boards/:boardId/lists/:listId') {
                def boardId = pathTokens['boardId']
                def listId = pathTokens['listId']
                byMethod {
                    delete {
                        int result = trelloCloneService.deleteBoardList(listId)
                        if(result > 0) {
                            response.send()
                        }
                        else {
                            clientError(404)
                        }
                    }
                }
            }

            path('lists/:listId/cards') {
                def listId = pathTokens['listId']
                byMethod {
                    post {
                        parse(jsonNode()).map { params ->
                            def name = params.get('name')?.textValue()
                            assert name

                            Card newCard = new Card(null, name, null, Integer.parseInt(listId))
                            trelloCloneService.addCard(newCard)
                        }.then {
                            response.send()
                        }
                    }

                    put {
                        parse(jsonNode()).map { params ->
                            log.info(params.toString())

                            def cardId = params.get('cardId')?.intValue()
                            def name = params.get('name')?.textValue()
                            def description = params.get('description')?.textValue()

                            assert cardId
                            assert name

                            Card card = new Card(cardId, name, description, Integer.parseInt(listId))

                            trelloCloneService.updateCard(card)
                        }.then {
                            response.send()
                        }
                    }
                }
            }

            path('cards/:cardId') {
                def cardId = pathTokens['cardId']
                byMethod {
                    delete {
                        int result = trelloCloneService.deleteCard(cardId)
                        if(result > 0) {
                            response.send()
                        }
                        else {
                            clientError(404)
                        }
                    }
                }
            }
        }
        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}