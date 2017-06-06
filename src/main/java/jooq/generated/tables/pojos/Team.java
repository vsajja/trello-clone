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
public class Team implements Serializable {

	private static final long serialVersionUID = -716476622;

	private final Integer teamId;
	private final String  name;
	private final String  description;

	public Team(Team value) {
		this.teamId = value.teamId;
		this.name = value.name;
		this.description = value.description;
	}

	public Team(
		Integer teamId,
		String  name,
		String  description
	) {
		this.teamId = teamId;
		this.name = name;
		this.description = description;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Team other = (Team) obj;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		}
		else if (!teamId.equals(other.teamId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Team (");

		sb.append(teamId);
		sb.append(", ").append(name);
		sb.append(", ").append(description);

		sb.append(")");
		return sb.toString();
	}
}
