package no.kristiania.taskManager.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public interface HttpController {
    void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws IOException, SQLException;

}
