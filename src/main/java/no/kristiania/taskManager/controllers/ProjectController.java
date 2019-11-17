package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.STATUS_CODE;
import no.kristiania.taskManager.jdbc.Project;
import no.kristiania.taskManager.jdbc.ProjectDao;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectController extends AbstractDaoController<ProjectDao> implements HttpController {


    public ProjectController(ProjectDao projectDao) {
        super(projectDao);
    }

    @Override
    public void handle(OutputStream outputStream, HttpRequest request) throws IOException {
        super.outputStream = outputStream;
        super.request = request;
        super.handle();

        switch (request.getRequestTarget()) {
            case "/api/projects?/listProjects=Option":
                handleList("option");
                break;
            case "/api/projects?/listProjects=Li":
                handleList("li");
                break;
            case "/api/projects?/addProject":
                handleAdd();
                break;
            case "/api/projects?/updateProject":
                handleUpdate();
            default:
                response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getBody(String htmlObject) throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<%s value='%s' id='%s'>%s</%s>", htmlObject, p.getId(), p.getId(), p.getName(), htmlObject))
                .collect(Collectors.joining(""));
    }

    @Override
    void insertData(Map<String, String> query) throws SQLException {
        if (query.containsKey("name")) {
            name = query.get("name");
        } else {
            throw new IllegalArgumentException();
        }

        //Creates new task object from POST-request data
        Project project = new Project();
        project.setName(name);

        //Inserts task in database
        dao.insert(project);
    }

    @Override
    void alterData(Map<String, String> requestBodyParameters) throws SQLException {
        if (requestBodyParameters.containsKey("id") && requestBodyParameters.containsKey("name")) {
            dao.update(requestBodyParameters.get("name"), Long.parseLong(requestBodyParameters.get("id")));
        } else {
            throw new IllegalArgumentException();
        }
    }


}
