package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.TaskMemberDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class MemberToTaskController implements HttpController {
    public MemberToTaskController(TaskMemberDao taskMemberDao) {

    }

    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws IOException, SQLException {


    }
}
