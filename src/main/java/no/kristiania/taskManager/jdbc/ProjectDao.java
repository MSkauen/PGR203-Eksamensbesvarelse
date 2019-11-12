package no.kristiania.taskManager.jdbc;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDao extends AbstractDao<Project>{

    public ProjectDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    protected void mapToStatement(Project project, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, project.getName());
    }

    @Override
    protected Project mapFromResultSet(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setName(rs.getString("name"));
        return project;
    }

    @Override
    protected Project retrieve(long id) throws SQLException {
        return null;
    }


    public void insert(Project project) throws SQLException {
        insert(project, "INSERT INTO projects (name) values (?)");
    }

    public List<Project> listAll() throws SQLException {
        return listAll("SELECT * FROM projects");
    }
}
