/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.records;


import javax.annotation.Generated;

import jooq.generated.tables.Board;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class BoardRecord extends UpdatableRecordImpl<BoardRecord> implements Record3<Integer, String, Integer> {

	private static final long serialVersionUID = 1781666662;

	/**
	 * Setter for <code>public.board.board_id</code>.
	 */
	public BoardRecord setBoardId(Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.board.board_id</code>.
	 */
	public Integer getBoardId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.board.name</code>.
	 */
	public BoardRecord setName(String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.board.name</code>.
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.board.user_id</code>.
	 */
	public BoardRecord setUserId(Integer value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.board.user_id</code>.
	 */
	public Integer getUserId() {
		return (Integer) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Integer> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Integer> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Board.BOARD.BOARD_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Board.BOARD.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Board.BOARD.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getBoardId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoardRecord value1(Integer value) {
		setBoardId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoardRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoardRecord value3(Integer value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoardRecord values(Integer value1, String value2, Integer value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached BoardRecord
	 */
	public BoardRecord() {
		super(Board.BOARD);
	}

	/**
	 * Create a detached, initialised BoardRecord
	 */
	public BoardRecord(Integer boardId, String name, Integer userId) {
		super(Board.BOARD);

		setValue(0, boardId);
		setValue(1, name);
		setValue(2, userId);
	}
}
