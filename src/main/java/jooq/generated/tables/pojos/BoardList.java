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

	private static final long serialVersionUID = -1821864084;

	private final Integer listId;
	private final String  name;
	private final Integer boardId;

	public BoardList(BoardList value) {
		this.listId = value.listId;
		this.name = value.name;
		this.boardId = value.boardId;
	}

	public BoardList(
		Integer listId,
		String  name,
		Integer boardId
	) {
		this.listId = listId;
		this.name = name;
		this.boardId = boardId;
	}

	public Integer getListId() {
		return this.listId;
	}

	public String getName() {
		return this.name;
	}

	public Integer getBoardId() {
		return this.boardId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BoardList other = (BoardList) obj;
		if (listId == null) {
			if (other.listId != null)
				return false;
		}
		else if (!listId.equals(other.listId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (boardId == null) {
			if (other.boardId != null)
				return false;
		}
		else if (!boardId.equals(other.boardId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listId == null) ? 0 : listId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((boardId == null) ? 0 : boardId.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("BoardList (");

		sb.append(listId);
		sb.append(", ").append(name);
		sb.append(", ").append(boardId);

		sb.append(")");
		return sb.toString();
	}
}
