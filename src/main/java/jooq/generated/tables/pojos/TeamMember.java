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
public class TeamMember implements Serializable {

	private static final long serialVersionUID = 880364833;

	private final Integer teamMemberId;
	private final Integer teamId;
	private final Integer userId;

	public TeamMember(TeamMember value) {
		this.teamMemberId = value.teamMemberId;
		this.teamId = value.teamId;
		this.userId = value.userId;
	}

	public TeamMember(
		Integer teamMemberId,
		Integer teamId,
		Integer userId
	) {
		this.teamMemberId = teamMemberId;
		this.teamId = teamId;
		this.userId = userId;
	}

	public Integer getTeamMemberId() {
		return this.teamMemberId;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TeamMember other = (TeamMember) obj;
		if (teamMemberId == null) {
			if (other.teamMemberId != null)
				return false;
		}
		else if (!teamMemberId.equals(other.teamMemberId))
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		}
		else if (!teamId.equals(other.teamId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		}
		else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamMemberId == null) ? 0 : teamMemberId.hashCode());
		result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("TeamMember (");

		sb.append(teamMemberId);
		sb.append(", ").append(teamId);
		sb.append(", ").append(userId);

		sb.append(")");
		return sb.toString();
	}
}
