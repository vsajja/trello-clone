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
import jooq.generated.tables.User;


/**
 * Convenience access to all tables in public
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * The table public.board
	 */
	public static final Board BOARD = jooq.generated.tables.Board.BOARD;

	/**
	 * The table public.board_list
	 */
	public static final BoardList BOARD_LIST = jooq.generated.tables.BoardList.BOARD_LIST;

	/**
	 * The table public.card
	 */
	public static final Card CARD = jooq.generated.tables.Card.CARD;

	/**
	 * The table public.team
	 */
	public static final Team TEAM = jooq.generated.tables.Team.TEAM;

	/**
	 * The table public.team_board
	 */
	public static final TeamBoard TEAM_BOARD = jooq.generated.tables.TeamBoard.TEAM_BOARD;

	/**
	 * The table public.user
	 */
	public static final User USER = jooq.generated.tables.User.USER;
}
