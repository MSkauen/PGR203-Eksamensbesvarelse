package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.STATUS_CODE;
import no.kristiania.taskManager.jdbc.Task;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskController extends AbstractDaoController<TaskDao> implements HttpController {

    public TaskController(TaskDao o) {
        super(o);
    }

    public void handle(OutputStream outputStream, HttpRequest request) throws IOException {
        super.outputStream = outputStream;
        super.request = request;
        super.handle();

            switch (request.getRequestTarget()) {
                case "/api/tasks?/listTasks=Option":
                    handleList("option");
                    break;
                case "/api/tasks?/listTasks=Li":
                    handleList("li");
                    break;
                case "/api/tasks?/addTask":
                    handleAdd();
                    break;
                case "api/tasks?/updateTasks":
                    handleUpdate();
                default: response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
            }
    }


    //IF REQUESTPATH IS /projects?/listProjects it will get tasks from tasks table to list
    @Override
    public String getBody(String htmlObject) throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<%s value='%s' id='%s'>%s</%s>", htmlObject, p.getId(), p.getId(), p.getName(), htmlObject))
                .collect(Collectors.joining(""));
    }

    //IF REQUESTPATH IS /task?/updateTask it will call on this method from the handler in AbstractDao to INSERT into the table
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


    //IF REQUESTPATH IS /task?/updateTask it will call on this method to alter the table
    @Override
    public void alterData(Map<String, String> requestBodyParameters) throws SQLException {

        //Checks if the request is correct
        if (requestBodyParameters.containsKey("id") && requestBodyParameters.containsKey("name") && requestBodyParameters.containsKey("status")) {
            //If
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

    private String findTaskStatus() {
        return null;
    }


}
