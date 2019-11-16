package no.kristiania.taskManager.jdbc;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao extends AbstractDao<Member> {

    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public Member retrieve(long id) throws SQLException {
        return retrieve(id, "SELECT * FROM MEMBERS where id = ?");
    }

    public List<Member> listAll() throws SQLException {
        return listAll("SELECT * FROM members");
    }

    public void insert(Member member) throws SQLException {
        long id = insert(member, "INSERT INTO members (name, age) VALUES (?, ?)");
        member.setId(id);
    }

    public void alter(String name, long id) throws SQLException {
        alter(name, id, "UPDATE members set name = ? WHERE id = ?");
    }

    public void alter(int age, long id) throws SQLException {
        alter(age, id, "UPDATE members set age = ? WHERE id = ?");
    }


    @Override
    protected void mapToStatement(Member member, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, member.getName());
        stmt.setInt(2, member.getAge());
    }

    @Override
    protected Member mapFromResultSet(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        member.setAge(rs.getInt("age"));
        return member;
    }

}
