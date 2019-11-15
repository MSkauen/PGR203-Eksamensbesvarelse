package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Task;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.sql.SQLException;
import java.util.Map;

public class AddTaskController extends AbstractAddController<TaskDao> {

    public AddTaskController(TaskDao o) {
        super(o);
    }


    public void insertData(Map<String, String> query) throws SQLException {
        //Gets data from POST-request hashMap
        String name = query.get("name");

        //Creates new task object from POST-request data
        Task task = new Task();
        task.setName(name);

        //Inserts task in database
        dao.insert(task);
    }

}
