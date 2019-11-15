package no.kristiania.taskManager.jdbc;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TaskDao extends AbstractDao<Task>{

    public TaskDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    public Task retrieve(long id) throws SQLException {
        return retrieve(id, "SELECT * FROM tasks where id = ?");
    }

    public List<Task> listAll() throws SQLException {
        return listAll("SELECT * FROM tasks");
    }

    public void insert(Task task) throws SQLException {
        long id = insert(task, "INSERT INTO tasks (name) VALUES (?)");
        task.setId(id);
    }

    public List<Task> listById(long id) throws SQLException {
        return listById(id, "SELECT * FROM TASKS WHERE id = ?");
    }

    @Override
    protected void mapToStatement(Task task, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, task.getName());
    }

    @Override
    protected Task mapFromResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setName(rs.getString("name"));
        return task;
    }






}
