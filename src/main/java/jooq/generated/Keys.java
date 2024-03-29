/**
 * This class is generated by jOOQ
 */
package jooq.generated;


import javax.annotation.Generated;

import jooq.generated.tables.Board;
import jooq.generated.tables.BoardList;
import jooq.generated.tables.Card;
import jooq.generated.tables.Team;
import jooq.generated.tables.TeamBoard;
import jooq.generated.tables.TeamMember;
import jooq.generated.tables.User;
import jooq.generated.tables.records.BoardListRecord;
import jooq.generated.tables.records.BoardRecord;
import jooq.generated.tables.records.CardRecord;
import jooq.generated.tables.records.TeamBoardRecord;
import jooq.generated.tables.records.TeamMemberRecord;
import jooq.generated.tables.records.TeamRecord;
import jooq.generated.tables.records.UserRecord;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>public</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final Identity<BoardRecord, Integer> IDENTITY_BOARD = Identities0.IDENTITY_BOARD;
	public static final Identity<BoardListRecord, Integer> IDENTITY_BOARD_LIST = Identities0.IDENTITY_BOARD_LIST;
	public static final Identity<CardRecord, Integer> IDENTITY_CARD = Identities0.IDENTITY_CARD;
	public static final Identity<TeamRecord, Integer> IDENTITY_TEAM = Identities0.IDENTITY_TEAM;
	public static final Identity<TeamBoardRecord, Integer> IDENTITY_TEAM_BOARD = Identities0.IDENTITY_TEAM_BOARD;
	public static final Identity<TeamMemberRecord, Integer> IDENTITY_TEAM_MEMBER = Identities0.IDENTITY_TEAM_MEMBER;
	public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<BoardRecord> BOARD_PKEY = UniqueKeys0.BOARD_PKEY;
	public static final UniqueKey<BoardListRecord> BOARD_LIST_PKEY = UniqueKeys0.BOARD_LIST_PKEY;
	public static final UniqueKey<CardRecord> CARD_PKEY = UniqueKeys0.CARD_PKEY;
	public static final UniqueKey<TeamRecord> TEAM_PKEY = UniqueKeys0.TEAM_PKEY;
	public static final UniqueKey<TeamBoardRecord> TEAM_BOARD_PKEY = UniqueKeys0.TEAM_BOARD_PKEY;
	public static final UniqueKey<TeamMemberRecord> TEAM_MEMBER_PKEY = UniqueKeys0.TEAM_MEMBER_PKEY;
	public static final UniqueKey<UserRecord> USER_PKEY = UniqueKeys0.USER_PKEY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final ForeignKey<BoardRecord, UserRecord> BOARD__BOARD_USER_USER_ID_FK = ForeignKeys0.BOARD__BOARD_USER_USER_ID_FK;
	public static final ForeignKey<BoardListRecord, BoardRecord> BOARD_LIST__BOARD_LIST_BOARD_BOARD_ID_FK = ForeignKeys0.BOARD_LIST__BOARD_LIST_BOARD_BOARD_ID_FK;
	public static final ForeignKey<CardRecord, BoardListRecord> CARD__CARD_BOARD_LIST_LIST_ID_FK = ForeignKeys0.CARD__CARD_BOARD_LIST_LIST_ID_FK;
	public static final ForeignKey<TeamBoardRecord, TeamRecord> TEAM_BOARD__TEAM_BOARD_TEAM_TEAM_ID_FK = ForeignKeys0.TEAM_BOARD__TEAM_BOARD_TEAM_TEAM_ID_FK;
	public static final ForeignKey<TeamBoardRecord, BoardRecord> TEAM_BOARD__TEAM_BOARD_BOARD_BOARD_ID_FK = ForeignKeys0.TEAM_BOARD__TEAM_BOARD_BOARD_BOARD_ID_FK;
	public static final ForeignKey<TeamMemberRecord, TeamRecord> TEAM_MEMBER__TEAM_MEMBER_TEAM_TEAM_ID_FK = ForeignKeys0.TEAM_MEMBER__TEAM_MEMBER_TEAM_TEAM_ID_FK;
	public static final ForeignKey<TeamMemberRecord, UserRecord> TEAM_MEMBER__TEAM_MEMBER_USER_USER_ID_FK = ForeignKeys0.TEAM_MEMBER__TEAM_MEMBER_USER_USER_ID_FK;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends AbstractKeys {
		public static Identity<BoardRecord, Integer> IDENTITY_BOARD = createIdentity(Board.BOARD, Board.BOARD.BOARD_ID);
		public static Identity<BoardListRecord, Integer> IDENTITY_BOARD_LIST = createIdentity(BoardList.BOARD_LIST, BoardList.BOARD_LIST.LIST_ID);
		public static Identity<CardRecord, Integer> IDENTITY_CARD = createIdentity(Card.CARD, Card.CARD.CARD_ID);
		public static Identity<TeamRecord, Integer> IDENTITY_TEAM = createIdentity(Team.TEAM, Team.TEAM.TEAM_ID);
		public static Identity<TeamBoardRecord, Integer> IDENTITY_TEAM_BOARD = createIdentity(TeamBoard.TEAM_BOARD, TeamBoard.TEAM_BOARD.TEAM_BOARD_ID);
		public static Identity<TeamMemberRecord, Integer> IDENTITY_TEAM_MEMBER = createIdentity(TeamMember.TEAM_MEMBER, TeamMember.TEAM_MEMBER.TEAM_MEMBER_ID);
		public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.USER_ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<BoardRecord> BOARD_PKEY = createUniqueKey(Board.BOARD, Board.BOARD.BOARD_ID);
		public static final UniqueKey<BoardListRecord> BOARD_LIST_PKEY = createUniqueKey(BoardList.BOARD_LIST, BoardList.BOARD_LIST.LIST_ID);
		public static final UniqueKey<CardRecord> CARD_PKEY = createUniqueKey(Card.CARD, Card.CARD.CARD_ID);
		public static final UniqueKey<TeamRecord> TEAM_PKEY = createUniqueKey(Team.TEAM, Team.TEAM.TEAM_ID);
		public static final UniqueKey<TeamBoardRecord> TEAM_BOARD_PKEY = createUniqueKey(TeamBoard.TEAM_BOARD, TeamBoard.TEAM_BOARD.TEAM_BOARD_ID);
		public static final UniqueKey<TeamMemberRecord> TEAM_MEMBER_PKEY = createUniqueKey(TeamMember.TEAM_MEMBER, TeamMember.TEAM_MEMBER.TEAM_MEMBER_ID);
		public static final UniqueKey<UserRecord> USER_PKEY = createUniqueKey(User.USER, User.USER.USER_ID);
	}

	private static class ForeignKeys0 extends AbstractKeys {
		public static final ForeignKey<BoardRecord, UserRecord> BOARD__BOARD_USER_USER_ID_FK = createForeignKey(jooq.generated.Keys.USER_PKEY, Board.BOARD, Board.BOARD.USER_ID);
		public static final ForeignKey<BoardListRecord, BoardRecord> BOARD_LIST__BOARD_LIST_BOARD_BOARD_ID_FK = createForeignKey(jooq.generated.Keys.BOARD_PKEY, BoardList.BOARD_LIST, BoardList.BOARD_LIST.BOARD_ID);
		public static final ForeignKey<CardRecord, BoardListRecord> CARD__CARD_BOARD_LIST_LIST_ID_FK = createForeignKey(jooq.generated.Keys.BOARD_LIST_PKEY, Card.CARD, Card.CARD.LIST_ID);
		public static final ForeignKey<TeamBoardRecord, TeamRecord> TEAM_BOARD__TEAM_BOARD_TEAM_TEAM_ID_FK = createForeignKey(jooq.generated.Keys.TEAM_PKEY, TeamBoard.TEAM_BOARD, TeamBoard.TEAM_BOARD.TEAM_ID);
		public static final ForeignKey<TeamBoardRecord, BoardRecord> TEAM_BOARD__TEAM_BOARD_BOARD_BOARD_ID_FK = createForeignKey(jooq.generated.Keys.BOARD_PKEY, TeamBoard.TEAM_BOARD, TeamBoard.TEAM_BOARD.BOARD_ID);
		public static final ForeignKey<TeamMemberRecord, TeamRecord> TEAM_MEMBER__TEAM_MEMBER_TEAM_TEAM_ID_FK = createForeignKey(jooq.generated.Keys.TEAM_PKEY, TeamMember.TEAM_MEMBER, TeamMember.TEAM_MEMBER.TEAM_ID);
		public static final ForeignKey<TeamMemberRecord, UserRecord> TEAM_MEMBER__TEAM_MEMBER_USER_USER_ID_FK = createForeignKey(jooq.generated.Keys.USER_PKEY, TeamMember.TEAM_MEMBER, TeamMember.TEAM_MEMBER.USER_ID);
	}
}
