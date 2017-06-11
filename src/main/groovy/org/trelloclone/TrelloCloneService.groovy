package org.trelloclone

import com.google.inject.Inject
import jooq.generated.tables.TeamBoard
import jooq.generated.tables.daos.CardDao
import jooq.generated.tables.pojos.Board
import jooq.generated.tables.pojos.BoardList
import jooq.generated.tables.pojos.Card
import jooq.generated.tables.pojos.Team
import jooq.generated.tables.pojos.TeamMember
import jooq.generated.tables.pojos.User
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.trelloclone.exceptions.InvalidCredentialsException

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

    public List<Board> getBoards(String userId) {
        List<Board> boards = context.selectFrom(BOARD)
                .where(BOARD.USER_ID.equal(userId))
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

    public Board createBoard(String userId, String name) {
        Board board = context.insertInto(BOARD)
                .set(BOARD.USER_ID, (String) userId)
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
     * @return Map of teams
     * key: team name
     * value: team details and team boards
     * TODO: add further documentation
     */
    def getTeams(String userId) {
        RecordMapper keyMapper = new RecordMapper() {
            Object map(Record record) {
                return record.into(TEAM).into(Team.class)
            }
        }

        RecordMapper valueMapper = new RecordMapper() {
            Object map(Record record) {
                // return null for null Board rows
                def boardId = record.getValue(BOARD.BOARD_ID)
                if (!boardId) {
                    return null
                }
                return record.into(BOARD).into(Board.class)
            }
        }

        def teams = context.select()
                .from(TEAM)
                .leftJoin(TEAM_BOARD).on(TEAM.TEAM_ID.eq(TEAM_BOARD.TEAM_ID))
                .leftJoin(BOARD).on(BOARD.BOARD_ID.eq(TEAM_BOARD.BOARD_ID))
                .leftJoin(TEAM_MEMBER).on(TEAM.TEAM_ID.eq(TEAM_MEMBER.TEAM_ID))
                .where(TEAM_MEMBER.USER_ID.equal(userId))
                .fetchGroups(keyMapper, valueMapper)

        def result = teams.collectEntries { Team team, List<Board> teamBoards ->
            [team.name, ['details': team, 'boards': (teamBoards - null)]]
        }

        return ['teams': result]
    }


    public Team createTeam(String name, String description) {
        Team team = context.insertInto(TEAM)
                .set(TEAM.NAME, name)
                .set(TEAM.DESCRIPTION, (String) description)
                .returning()
                .fetchOne()
                .into(Team.class)
        return team
    }

    public Team createTeam(String userId, String name, String description) {
        Team team = null
        context.transaction {
            team = createTeam(name, description)
            TeamMember teamMember = context.insertInto(TEAM_MEMBER)
                        .set(TEAM_MEMBER.TEAM_ID, team.teamId)
                        .set(TEAM_MEMBER.USER_ID, userId)
                        .returning()
                        .fetchOne()
                        .into(TeamMember.class)
            team
        }
        return team
    }

    public TeamBoard createTeamBoard(String teamId, String name) {
        TeamBoard teamBoard = null

        context.transaction {
            Board board = createBoard(null, name)

            teamBoard = context.insertInto(TEAM_BOARD)
                    .set(TEAM_BOARD.TEAM_ID, teamId)
                    .set(TEAM_BOARD.BOARD_ID, board.getBoardId())
                    .returning()
                    .fetchOne()
                    .into(TeamBoard.class)

            return teamBoard
        }
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
                if (!cardId) {
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
            [list.listId, ['details': list, 'cards': (cards - null)]]
        }

        return ['lists': result]
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

    public void addCard(Card card) {
        cardDao.insert(card)
    }

    /**
     *
     * @param cardId
     * @return
     * the number of deleted records
     */
    public int deleteCard(String cardId) {
        def result = context.delete(CARD)
                .where(CARD.CARD_ID.equal(cardId))
                .execute()
        return result
    }

    public User getUser(String username) {
        User user = null
        def userData = context.selectFrom(USER)
                .where(USER.USERNAME.equal(username))
                .fetchOne()
        if (userData) {
            user = userData.into(User.class)
        }
        return user
    }

    public User registerUser(String username, String password) {
        int BCRYPT_LOG_ROUNDS = 6
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_LOG_ROUNDS))
        def user = context.insertInto(USER)
                .set(USER.USERNAME, username)
                .set(USER.PASSWORD, hashedPassword)
                .returning()
                .fetchOne()
                .into(User.class)
        return user
    }

    public User login(String username, String password) throws InvalidCredentialsException {
        User user = getUser(username)
        if (user) {
            boolean passwordMatches = BCrypt.checkpw(password, user.getPassword())
            if (passwordMatches) {
                return new User(user.userId, user.username, null)
            } else {
                throw new InvalidCredentialsException('Invalid username and password')
            }
        } else {
            throw new InvalidCredentialsException('User not found ' + username)
        }
    }
}
