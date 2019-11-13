package no.kristiania.taskManager.jdbc;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Task_MemberDao extends AbstractDao<TaskMember>{
    public Task_MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public TaskMember retrieve(long id) throws SQLException {
        return retrieve(id, "SELECT * FROM task_members where id = ?");
    }

    public TaskMember retrieveMemberProjects(long memberId) throws SQLException {
        return retrieve(memberId, "SELECT * FROM task_members WHERE member_id = ?");
    }

    public TaskMember retrieveProjectMembers(long projectId) throws SQLException {
        return retrieve(projectId, "SELECT * FROM task_members WHERE project_id = ?");
    }

    public List<TaskMember> listAll() throws SQLException {
        return listAll("SELECT * FROM task_members");
    }

    public void insert(TaskMember task_member) throws SQLException {
        long id = insert(task_member,"INSERT INTO task_members (task_id, member_id) VALUES (?, ?)");
        task_member.setId(id);
    }

    @Override
    protected void mapToStatement(TaskMember task_member, PreparedStatement stmt) throws SQLException {
        stmt.setLong(1, task_member.getProjectId());
        stmt.setLong(2, task_member.getMemberId());
    }

    @Override
    protected TaskMember mapFromResultSet(ResultSet rs) throws SQLException {
        TaskMember task_member = new TaskMember();
        task_member.setId(rs.getLong("id"));
        task_member.setMemberId(rs.getLong("member_id"));
        task_member.setProjectId(rs.getLong("task_id"));

        return task_member;
    }

}
