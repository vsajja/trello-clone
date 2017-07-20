package org.trelloclone

import groovy.json.JsonOutput
import jooq.generated.tables.daos.UserDao
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.trelloclone.postgres.PostgresConfig
import org.trelloclone.postgres.PostgresModule
import ratpack.http.client.RequestSpec
import spock.lang.Shared
import spock.lang.Specification

import javax.sql.DataSource

class TrelloCloneSpec extends Specification {
    @Shared
    DSLContext context

    @Shared
    UserDao userDao

    def setupSpec() {
        Properties props = new Properties()
        props.load(new FileInputStream('src/ratpack/db.properties'))

        PostgresConfig postgresConfig = new PostgresConfig()
        postgresConfig.user = props.get('postgres.user')
        postgresConfig.password = props.get('postgres.password')
        postgresConfig.serverName = props.get('postgres.serverName')
        postgresConfig.databaseName = props.get('postgres.databaseName')

        DataSource dataSource = new PostgresModule().dataSource(postgresConfig)
        Configuration configuration = new DefaultConfiguration().set(dataSource).set(SQLDialect.POSTGRES)

        userDao = new UserDao(configuration)
        context = DSL.using(dataSource, SQLDialect.POSTGRES);
    }


    /**
     * Creates a request spec for the user used by the login and register endpoints
     * @param username
     * @param password
     * @return
     */
    def createUserRequestSpec(String username, String password) {
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [username: username,
                     password: password])
            )
        }
    }

    /**
     * Creates a request spec for creating a new board
     * @param boardName
     * @return
     */
    def createBoardRequestSpec(String boardName) {
        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [name: boardName])
            )
        }
    }
}