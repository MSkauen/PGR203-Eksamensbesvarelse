package no.kristiania.taskManager.jdbc;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDao extends AbstractDao<Project> {

    public ProjectDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Project retrieve(long id) throws SQLException {
        return retrieve(id, "SELECT * FROM projects where id = ?");
    }

    public List<Project> listAll() throws SQLException {
        System.out.println("I got to listall");
        return listAll("SELECT * FROM projects");
    }

    public void insert(Project project) throws SQLException {
        long id = insert(project, "INSERT INTO projects (name) VALUES (?)");
        project.setId(id);
    }

    public void update(String name, long id) throws SQLException {
        update(name, id, "UPDATE projects set name = ? WHERE id = ?");
    }

    @Override
    protected void mapToStatement(Project project, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, project.getName());
    }

    @Override
    protected Project mapFromResultSet(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getLong("id"));
        project.setName(rs.getString("name"));
        return project;
    }


}


