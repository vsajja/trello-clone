package org.trelloclone

import com.google.inject.Inject
import jooq.generated.tables.pojos.Board
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static jooq.generated.Tables.*;

import javax.sql.DataSource

/**
 * @author vsajja
 */
class TrelloCloneService {
    final Logger log = LoggerFactory.getLogger(this.class)

    DSLContext context

    @Inject
    public TrelloCloneService(DataSource dataSource) {
        context = DSL.using(dataSource, SQLDialect.POSTGRES)
    }

    public List<Board> getBoards() {
        List<Board> boards = context.selectFrom(BOARD)
                .fetch()
                .into(Board.class)
        return boards
    }

    public List<Board> createBoard() {
        List<Board> boards = context.selectFrom(BOARD)
                .fetch()
                .into(Board.class)
        return boards
    }
}
