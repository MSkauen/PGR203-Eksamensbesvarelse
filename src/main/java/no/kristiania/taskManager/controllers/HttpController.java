package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public interface HttpController {
    void handle(OutputStream outputStream, HttpRequest request) throws IOException, SQLException;

}
