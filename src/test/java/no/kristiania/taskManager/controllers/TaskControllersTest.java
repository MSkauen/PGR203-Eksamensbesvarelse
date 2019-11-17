package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.controllers.*;
import no.kristiania.taskManager.jdbc.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskControllersTest {

    private TaskDao dao = new TaskDao(TestDatabase.testDataSource());
    private ProjectDao projectDao = new ProjectDao(TestDatabase.testDataSource());

    @Test
    void shouldReturnTaskFromDatabase() throws SQLException {
        Task task1 = TaskDaoTest.sampleTask();
        Task task2 = TaskDaoTest.sampleTask();

        dao.insert(task1);
        dao.insert(task2);

        TaskController controller = new TaskController(dao, projectDao);
        assertThat(controller.getBody("option"))
                .contains(String.format("<option value='%s' id='%s'>NAME: %s <br> STATUS: %s <br> PROJECT: %s</option>", task1.getId(), task1.getId(), task1.getName(), task1.getTaskStatus(),
                        (projectDao.retrieve(task1.getProjectId()) == null ? "Not set to project" : projectDao.retrieve(task1.getProjectId()))))
                .contains(String.format("<option value='%s' id='%s'>NAME: %s <br> STATUS: %s <br> PROJECT: %s</option>", task2.getId(), task2.getId(), task2.getName(), task2.getTaskStatus(),
                        (projectDao.retrieve(task2.getProjectId()) == null ? "Not set to project" : projectDao.retrieve(task2.getProjectId()))));

    }

    @Test
    void shouldInsertMembersToDatabase() throws SQLException {
        Task task1 = TaskDaoTest.sampleTask();

        Map<String, String> data = getTaskDataMap(task1);

        TaskController controller = new TaskController(dao, projectDao);
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

        TaskController controller = new TaskController(dao, projectDao);
        controller.alterData(getDataMapForAltering(task1, task2));

        assertEquals(dao.retrieve(task1.getId()).getName(), dao.retrieve(task2.getId()).getName());

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
