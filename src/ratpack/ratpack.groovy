import com.zaxxer.hikari.HikariConfig
import jooq.generated.tables.pojos.Board
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.trelloclone.TrelloCloneService
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

            path('boards') {
                byMethod {
                    get {
                        def boards = trelloCloneService.getBoards()
                        render json(boards)
                    }

                    post {
                        parse(jsonNode()).map { params ->
                            def name = params.get('name')?.textValue()
                            assert name

                            trelloCloneService.createBoard(name)
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
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}