package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.Task;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class AddTaskController extends TaskController {

    public AddTaskController(TaskDao o) {
        super(o);
    }

    public void handle(OutputStream outputStream, HttpRequest request) throws IOException, SQLException {
        super.handle(outputStream, request);
        handleAdd();
    }
}
