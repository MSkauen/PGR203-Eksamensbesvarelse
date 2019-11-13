package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.ProjectDao;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class ProjectsController extends AbstractListController<ProjectDao> {

    public ProjectsController(ProjectDao o) {
        super(o);
    }

    @Override
    public String getBody() throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<option id='%s'>%s</option>", p.getId(), p.getName()))
                .collect(Collectors.joining(""));
    }
}


