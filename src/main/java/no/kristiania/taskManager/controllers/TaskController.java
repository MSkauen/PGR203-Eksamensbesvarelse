package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.STATUS_CODE;
import no.kristiania.taskManager.jdbc.ProjectDao;
import no.kristiania.taskManager.jdbc.TASK_STATUS;
import no.kristiania.taskManager.jdbc.Task;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskController extends AbstractDaoController<TaskDao> implements HttpController {

    ProjectDao projectDao;

    public TaskController(TaskDao o, ProjectDao projectDao) {
        super(o);
        this.projectDao = projectDao;
    }

    public void handle(OutputStream outputStream, HttpRequest request) throws IOException {
        super.outputStream = outputStream;
        super.request = request;
        super.handle();

        //Depending on the different requestpaths we call different methods in the abstractController.
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
            case "/api/tasks?/updateTask":
                System.out.println("I GOT HERE");
                handleUpdate();
            default:
                response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getBody(String htmlObject) throws SQLException {
        return dao.listAll().stream()
                .map(p -> {
                    try {
                        return String.format("<%s value='%s' id='%s'>NAME: %s <br> STATUS: %s <br> PROJECT: %s</%s>",
                                htmlObject, p.getId(), p.getId(), p.getName(), p.getTaskStatus(),
                                (projectDao.retrieve(p.getProjectId()) == null ? "Not set to project" : projectDao.retrieve(p.getProjectId()).getName()),
                                htmlObject);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.joining(""));
    }

    public void insertData(Map<String, String> query) throws SQLException {

        if (query.containsKey("name")) {
            name = query.get("name");
        } else {
            throw new IllegalArgumentException();
        }

        Task task = new Task();
        task.setName(name);

        dao.insert(task);
    }

    @Override
    public void alterData(Map<String, String> requestBodyParameters) throws SQLException {

        //Checks if the request is correct
        if (requestBodyParameters.containsKey("id") && requestBodyParameters.containsKey("name") && requestBodyParameters.containsKey("status") && requestBodyParameters.containsKey("projectId")) {
            //If
            if (!(requestBodyParameters.get("name").isBlank())) {
                dao.update(requestBodyParameters.get("name"), Long.parseLong(requestBodyParameters.get("id")));
            }
            if (!(requestBodyParameters.get("status").isBlank())) {
                dao.update(TASK_STATUS.getTaskStatus(requestBodyParameters.get("status")), Long.parseLong(requestBodyParameters.get("id")));
            }
            if(!(requestBodyParameters.get("projectId").isBlank())){
                dao.update(Long.parseLong(requestBodyParameters.get("projectId")), Long.parseLong(requestBodyParameters.get("id")));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private String findTaskStatus() {
        return null;
    }


}
