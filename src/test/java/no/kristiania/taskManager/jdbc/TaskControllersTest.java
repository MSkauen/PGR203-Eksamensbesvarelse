package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.controllers.AddTaskController;
import no.kristiania.taskManager.controllers.UpdateTaskController;
import no.kristiania.taskManager.controllers.ListTasksController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskControllersTest {

    private TaskDao dao = new TaskDao(TestDatabase.testDataSource());

    @Test
    void shouldReturnTaskFromDatabase() throws SQLException {
        Task task1 = TaskDaoTest.sampleTask();
        Task task2 = TaskDaoTest.sampleTask();

        dao.insert(task1);
        dao.insert(task2);

        ListTasksController controller = new ListTasksController(dao);
        assertThat(controller.getBody("option"))
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

    @Test
    void shouldAlterExistingMember() throws SQLException {
        Task task1 = TaskDaoTest.sampleTask();
        Task task2 = TaskDaoTest.sampleTask();

        dao.insert(task1);
        dao.insert(task2);

        UpdateTaskController controller = new UpdateTaskController(dao);
        controller.alterData(getDataMapForAltering(task1, task2));

        assertEquals(dao.retrieve(task1.getId()).getName(), dao.retrieve(task2.getId()).getName());
        System.out.println(dao.retrieve(task1.getId()).getTaskStatus());


    }

    private Map<String, String> getDataMapForAltering(Task task1, Task task2) {
        Map<String, String> data = new HashMap<>();
        data.put("id", Long.toString(task1.getId()));
        data.put("name", task2.getName());
        data.put("status", task2.getTaskStatus());

        return data;
    }


    private Map<String, String> getTaskDataMap(Task task) {

        Map<String, String> data = new HashMap<>();
        data.put("name", task.getName());

        return data;
    }
}
