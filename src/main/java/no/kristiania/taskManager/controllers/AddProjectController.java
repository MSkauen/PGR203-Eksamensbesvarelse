package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Project;
import no.kristiania.taskManager.jdbc.ProjectDao;

import java.sql.SQLException;
import java.util.Map;

public class AddProjectController extends AbstractAddController {

    private final ProjectDao dao;
    public AddProjectController(ProjectDao projectDao) {
        this.dao = projectDao;
    }


    public void insertData(Map<String, String> query) throws SQLException {
        //Gets data from POST-request hashMap
        String name = query.get("name");

        //Creates new project object from POST-request data
        Project project = new Project();
        project.setName(name);

        //Inserts project in database
        dao.insert(project);
    }

}
