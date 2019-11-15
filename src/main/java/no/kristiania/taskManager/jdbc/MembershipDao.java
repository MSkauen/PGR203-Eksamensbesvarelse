package no.kristiania.taskManager.jdbc;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MembershipDao extends AbstractDao<Membership>{
    public MembershipDao(DataSource dataSource) {
        super(dataSource);
    }

    public Membership retrieve(long id) throws SQLException {
        return retrieve(id, "SELECT * FROM task_members where id = ?");
    }

    public List<Membership> listAll() throws SQLException {
        return listAll("SELECT * FROM task_members");
    }

    public List<Membership> listTasksByMemberId(long id) throws SQLException{
        return listById(id, "SELECT * FROM task_members where member_id = ?");
    }

    public List<Membership> listMembersByTaskId(long id) throws SQLException{
        return listById(id, "SELECT * FROM task_members where task_id = ?");
    }

    public void insert(Membership task_member) throws SQLException {
        long id = insert(task_member,"INSERT INTO task_members (task_id, member_id) VALUES (?, ?)");
        task_member.setId(id);
    }

    @Override
    protected void mapToStatement(Membership task_member, PreparedStatement stmt) throws SQLException {
        stmt.setLong(1, task_member.getTaskId());
        stmt.setLong(2, task_member.getMemberId());
    }

    @Override
    protected Membership mapFromResultSet(ResultSet rs) throws SQLException {
        Membership task_member = new Membership();
        task_member.setId(rs.getLong("id"));
        task_member.setMemberId(rs.getLong("member_id"));
        task_member.setTaskId(rs.getLong("task_id"));

        return task_member;
    }

}
