package org.trelloclone

import com.google.inject.Inject
import jooq.generated.tables.daos.CardDao
import jooq.generated.tables.pojos.Board
import jooq.generated.tables.pojos.BoardList
import jooq.generated.tables.pojos.Card
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static jooq.generated.Tables.*;
import static ratpack.jackson.Jackson.json

import javax.sql.DataSource

/**
 * @author vsajja
 */
class TrelloCloneService {
    final Logger log = LoggerFactory.getLogger(this.class)

    DSLContext context

    CardDao cardDao

    @Inject
    public TrelloCloneService(DataSource dataSource) {
        context = DSL.using(dataSource, SQLDialect.POSTGRES)
        cardDao = new CardDao(context.configuration())
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

    /**
     *
     * @param boardId
     * @return
     * Map of list ids and list contents (list details and assigned cards)
     * TODO: add further documentation
     */
    def getBoardLists(String boardId) {
        RecordMapper keyMapper = new RecordMapper() {
            Object map(Record record) {
                return record.into(BOARD_LIST).into(BoardList.class)
            }
        }

        RecordMapper valueMapper = new RecordMapper() {
            Object map(Record record) {
                // return null for null Card rows
                def cardId = record.getValue(CARD.CARD_ID)
                if(!cardId)  {
                    return null
                }
                return record.into(CARD).into(Card.class)
            }
        }

        Map<BoardList, List<Card>> boardLists = context.select()
                .from(BOARD_LIST)
                .leftJoin(CARD)
                .on(CARD.LIST_ID.eq(BOARD_LIST.LIST_ID))
                .where(BOARD_LIST.BOARD_ID.equal(boardId))
                .fetchGroups(keyMapper, valueMapper)


        def result = boardLists.collectEntries { BoardList list, List<Card> cards ->
            [list.listId, ['details' : list, 'cards' : (cards - null)]]
        }

        return ['lists' : result]
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
     * @param listId
     * @return
     * the number of deleted records
     */
    public int deleteBoardList(String listId) {
        def result = context.delete(BOARD_LIST)
                .where(BOARD_LIST.LIST_ID.equal(listId))
                .execute()
        return result
    }

    public void updateCards(List<Card> cardsToUpdate) {
        cardDao.update(cardsToUpdate)
    }
}
