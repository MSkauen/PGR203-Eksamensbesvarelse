package no.kristiania.taskManager.jdbc;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskDaoTest {

    private DataSource dataSource = TestDatabase.testDataSource();

    @Test
    void shouldFindSavedTasks() throws SQLException {

        Task task = sampleTask();
        TaskDao dao = new TaskDao(dataSource);

        dao.insert(task);
        assertThat(dao.listAll()).contains(task);
    }

    public static Task sampleTask() {
        Task task = new Task();
        task.setName(sampleTaskName());
        return task;
    }

    private static String sampleTaskName() {
        String[] tasks = {"Task1", "Task2", "Task3"};
        return tasks[new Random().nextInt(tasks.length)];
    }


}
