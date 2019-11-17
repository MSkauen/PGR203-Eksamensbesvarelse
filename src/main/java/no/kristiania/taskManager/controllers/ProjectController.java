package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Project;
import no.kristiania.taskManager.jdbc.ProjectDao;
import no.kristiania.taskManager.jdbc.Task;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectController extends AbstractDaoController<ProjectDao> {

    protected ProjectController(ProjectDao o) {
        super(o);
    }

    @Override
    public String getBody(String htmlObject) throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<%s value='%s' id='%s'>%s</%s>",htmlObject, p.getId(), p.getId(), p.getName(), htmlObject))
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
        if(requestBodyParameters.containsKey("id") && requestBodyParameters.containsKey("name")){
            dao.update(requestBodyParameters.get("name"), Long.parseLong(requestBodyParameters.get("id")));
        } else {
            throw new IllegalArgumentException();
        }
    }
}
