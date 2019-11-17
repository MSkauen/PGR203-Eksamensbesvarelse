package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Task;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskController extends AbstractDaoController<TaskDao>{

    protected TaskController(TaskDao o) {
        super(o);
    }

    @Override
    public String getBody() throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<option value='%s' id='%s'>%s</option>", p.getId(), p.getId(), p.getName()))
                .collect(Collectors.joining(""));
    }

    public void insertData(Map<String, String> query) throws SQLException {

        //Gets data from POST-request hashMap
        if (query.containsKey("name")) {
            name = query.get("name");
        } else {
            throw new IllegalArgumentException();
        }

        //Creates new task object from POST-request data
        Task task = new Task();
        task.setName(name);

        //Inserts task in database
        dao.insert(task);
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
