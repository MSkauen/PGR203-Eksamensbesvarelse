package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.jdbc.TaskDao;
import no.kristiania.taskManager.jdbc.TaskManagerServer;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class ListTasksController extends TaskController {

    public ListTasksController(TaskDao o) {
        super(o);
    }

    public void handle(OutputStream outputStream, HttpRequest request) throws IOException, SQLException {
        super.handle(outputStream, request);
        handleList();
    }
}
