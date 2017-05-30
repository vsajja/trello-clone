/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class BoardList implements Serializable {

	private static final long serialVersionUID = -1650079515;

	private final Integer boardListId;
	private final String  name;
	private final Integer boardId;

	public BoardList(BoardList value) {
		this.boardListId = value.boardListId;
		this.name = value.name;
		this.boardId = value.boardId;
	}

	public BoardList(
		Integer boardListId,
		String  name,
		Integer boardId
	) {
		this.boardListId = boardListId;
		this.name = name;
		this.boardId = boardId;
	}

	public Integer getBoardListId() {
		return this.boardListId;
	}

	public String getName() {
		return this.name;
	}

	public Integer getBoardId() {
		return this.boardId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("BoardList (");

		sb.append(boardListId);
		sb.append(", ").append(name);
		sb.append(", ").append(boardId);

		sb.append(")");
		return sb.toString();
	}
}