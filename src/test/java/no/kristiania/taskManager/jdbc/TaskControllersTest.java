package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.controllers.AddTaskController;
import no.kristiania.taskManager.controllers.ListTasksController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskControllersTest {

    private TaskDao dao = new TaskDao(TestDatabase.testDataSource());

    @Test
    void shouldReturnTaskFromDatabase() throws SQLException {
        Task task1 = TaskDaoTest.sampleTask();
        Task task2 = TaskDaoTest.sampleTask();

        dao.insert(task1);
        dao.insert(task2);

        ListTasksController controller = new ListTasksController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option value='%s' id='%s'>%s</option>", task1.getId(), task1.getId(), task1.getName()))
                .contains(String.format("<option value='%s' id='%s'>%s</option>", task2.getId(), task2.getId(), task2.getName()));
    }

    @Test
    void shouldInsertMembersToDatabase() throws SQLException {
        Task task1 = TaskDaoTest.sampleTask();

        Map<String, String> data = getTaskDataMap(task1);

        AddTaskController controller = new AddTaskController(dao);
        controller.insertData(data);

        assertThat(dao.listAll())
                .contains(task1);

    }

    private Map<String, String> getTaskDataMap(Task task) {

        Map<String, String> data = new HashMap<>();
        data.put("name", task.getName());

        return data;
    }
}
