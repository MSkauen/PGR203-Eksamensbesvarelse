package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Project;
import no.kristiania.taskManager.jdbc.ProjectDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class AddProjectController implements HttpController {


    private final ProjectDao dao;

    public AddProjectController(ProjectDao projectDao) {
        this.dao = projectDao;
    }

    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws IOException, SQLException {
        addProject(query);
    }

    void addProject(Map<String, String> query) throws SQLException {
        //Gets data from POST-request hashMap
        String name = query.get("name");

        //Creates new project object from POST-request data
        Project project = new Project();
        project.setName(name);

        //Inserts project in database
        dao.insert(project);
    }

}
