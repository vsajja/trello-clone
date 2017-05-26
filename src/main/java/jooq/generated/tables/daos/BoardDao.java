/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import jooq.generated.tables.Board;
import jooq.generated.tables.records.BoardRecord;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BoardDao extends DAOImpl<BoardRecord, jooq.generated.tables.pojos.Board, Integer> {

	/**
	 * Create a new BoardDao without any configuration
	 */
	public BoardDao() {
		super(Board.BOARD, jooq.generated.tables.pojos.Board.class);
	}

	/**
	 * Create a new BoardDao with an attached configuration
	 */
	public BoardDao(Configuration configuration) {
		super(Board.BOARD, jooq.generated.tables.pojos.Board.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(jooq.generated.tables.pojos.Board object) {
		return object.getBoardId();
	}

	/**
	 * Fetch records that have <code>board_id IN (values)</code>
	 */
	public List<jooq.generated.tables.pojos.Board> fetchByBoardId(Integer... values) {
		return fetch(Board.BOARD.BOARD_ID, values);
	}

	/**
	 * Fetch a unique record that has <code>board_id = value</code>
	 */
	public jooq.generated.tables.pojos.Board fetchOneByBoardId(Integer value) {
		return fetchOne(Board.BOARD.BOARD_ID, value);
	}

	/**
	 * Fetch records that have <code>name IN (values)</code>
	 */
	public List<jooq.generated.tables.pojos.Board> fetchByName(String... values) {
		return fetch(Board.BOARD.NAME, values);
	}
}
