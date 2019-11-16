package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.Task;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.sql.SQLException;
import java.util.Map;

public class AddTaskController extends AbstractAddController<TaskDao> {

    private String name;

    public AddTaskController(TaskDao o) {
        super(o);
    }


    public void insertData(Map<String, String> query) throws SQLException {

        //Gets data from POST-request hashMap
        if(query.containsKey("name")){
            name = query.get("name");
        }  else {
            throw new IllegalArgumentException();
        }

        //Creates new task object from POST-request data
        Task task = new Task();
        task.setName(name);

        //Inserts task in database
        dao.insert(task);
    }

}
