package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.jdbc.MemberDao;
import no.kristiania.taskManager.jdbc.ProjectDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class ListProjectsController extends ProjectController {

        public ListProjectsController(ProjectDao o) {
            super(o);
        }

        public void handle(OutputStream outputStream, HttpRequest request) throws IOException, SQLException {
            super.handle(outputStream, request);
            handleList("option");
        }

}
