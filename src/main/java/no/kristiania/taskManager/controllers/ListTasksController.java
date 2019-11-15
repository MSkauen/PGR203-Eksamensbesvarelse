package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.TaskDao;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class ListTasksController extends AbstractListController<TaskDao> {

    public ListTasksController(TaskDao o) {
        super(o);
    }

    @Override
    public String getBody() throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<option value='%s' id='%s'>%s</option>", p.getId(), p.getId(), p.getName()))
                .collect(Collectors.joining(""));
    }
}


