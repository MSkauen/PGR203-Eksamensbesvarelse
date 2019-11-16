package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Task;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.sql.SQLException;
import java.util.Map;

public class UpdateTaskController extends AbstractUpdateController<TaskDao> {

    public UpdateTaskController(TaskDao o) {
        super(o);
    }

    @Override
    public void alterData(Map<String, String> requestBodyParameters) throws SQLException {

        if(requestBodyParameters.containsKey("id") && requestBodyParameters.containsKey("name") && requestBodyParameters.containsKey("status")){
            if (!(requestBodyParameters.get("name").isBlank())) {
                dao.update(requestBodyParameters.get("name"), Long.parseLong(requestBodyParameters.get("id")));
            }
            if (!(requestBodyParameters.get("status").isBlank())) {
                dao.update(Task.TASK_STATUS.IN_PROGRESS, Long.parseLong(requestBodyParameters.get("id")));
            }
        } else {
        throw new IllegalArgumentException();
        }
    }
}

