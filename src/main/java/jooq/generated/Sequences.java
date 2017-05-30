/**
 * This class is generated by jOOQ
 */
package jooq.generated;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

	/**
	 * The sequence <code>public.board_board_id_seq</code>
	 */
	public static final Sequence<Long> BOARD_BOARD_ID_SEQ = new SequenceImpl<Long>("board_board_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.board_list_board_list_id_seq</code>
	 */
	public static final Sequence<Long> BOARD_LIST_BOARD_LIST_ID_SEQ = new SequenceImpl<Long>("board_list_board_list_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.user_user_id_seq</code>
	 */
	public static final Sequence<Long> USER_USER_ID_SEQ = new SequenceImpl<Long>("user_user_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
