/**
 * This class is generated by jOOQ
 */
package jooq.generated.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooq.generated.Keys;
import jooq.generated.Public;
import jooq.generated.tables.records.TeamMemberRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class TeamMember extends TableImpl<TeamMemberRecord> {

	private static final long serialVersionUID = 1913119683;

	/**
	 * The reference instance of <code>public.team_member</code>
	 */
	public static final TeamMember TEAM_MEMBER = new TeamMember();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TeamMemberRecord> getRecordType() {
		return TeamMemberRecord.class;
	}

	/**
	 * The column <code>public.team_member.team_member_id</code>.
	 */
	public final TableField<TeamMemberRecord, Integer> TEAM_MEMBER_ID = createField("team_member_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.team_member.team_id</code>.
	 */
	public final TableField<TeamMemberRecord, Integer> TEAM_ID = createField("team_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>public.team_member.user_id</code>.
	 */
	public final TableField<TeamMemberRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * Create a <code>public.team_member</code> table reference
	 */
	public TeamMember() {
		this("team_member", null);
	}

	/**
	 * Create an aliased <code>public.team_member</code> table reference
	 */
	public TeamMember(String alias) {
		this(alias, TEAM_MEMBER);
	}

	private TeamMember(String alias, Table<TeamMemberRecord> aliased) {
		this(alias, aliased, null);
	}

	private TeamMember(String alias, Table<TeamMemberRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TeamMemberRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TEAM_MEMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TeamMemberRecord> getPrimaryKey() {
		return Keys.TEAM_MEMBER_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TeamMemberRecord>> getKeys() {
		return Arrays.<UniqueKey<TeamMemberRecord>>asList(Keys.TEAM_MEMBER_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<TeamMemberRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TeamMemberRecord, ?>>asList(Keys.TEAM_MEMBER__TEAM_MEMBER_TEAM_TEAM_ID_FK, Keys.TEAM_MEMBER__TEAM_MEMBER_USER_USER_ID_FK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeamMember as(String alias) {
		return new TeamMember(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TeamMember rename(String name) {
		return new TeamMember(name, null);
	}
}
