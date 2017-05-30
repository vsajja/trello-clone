package org.trelloclone

import com.google.inject.Inject
import jooq.generated.tables.pojos.Board
import jooq.generated.tables.pojos.BoardList
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

    /**
     *
     * @param boardId
     * @return
     * null if the board is not found
     */
    public Board getBoard(String boardId) {
        Board board = null
        def boardData = context.selectFrom(BOARD)
                .where(BOARD.BOARD_ID.equal(boardId))
                .fetchOne()
        if (boardData) {
            board = boardData.into(Board.class)
        }
        return board
    }

    public Board createBoard(String name) {
        Board board = context.insertInto(BOARD)
                .set(BOARD.NAME, name)
                .returning()
                .fetchOne()
                .into(Board.class)
        return board
    }

    /**
     *
     * @param boardId
     * @return
     * the number of deleted records
     */
    public int deleteBoard(String boardId) {
        def result = context.delete(BOARD)
                .where(BOARD.BOARD_ID.equal(boardId))
                .execute()
        return result
    }

    public List<BoardList> getBoardLists(String boardId) {
        List<BoardList> boardLists = context.selectFrom(BOARD_LIST)
                .where(BOARD_LIST.BOARD_ID.equal(boardId))
                .fetch()
                .into(BoardList.class)
        return boardLists
    }

    public BoardList createBoardList(String boardId, String name) {
        BoardList boardList = context.insertInto(BOARD_LIST)
                .set(BOARD_LIST.NAME, name)
                .set(BOARD.BOARD_ID, boardId)
                .returning()
                .fetchOne()
                .into(BoardList.class)
        return boardList
    }

    /**
     * @param boardListId
     * @return
     * the number of deleted records
     */
    public int deleteBoardList(String boardListId) {
        def result = context.delete(BOARD_LIST)
                .where(BOARD_LIST.BOARD_LIST_ID.equal(boardListId))
                .execute()
        return result
    }
}
