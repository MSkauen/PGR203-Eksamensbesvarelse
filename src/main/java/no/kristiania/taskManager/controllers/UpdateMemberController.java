package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.jdbc.MemberDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class UpdateMemberController extends MemberController {

    public UpdateMemberController(MemberDao o) {
        super(o);
    }

    public void handle(OutputStream outputStream, HttpRequest request) throws IOException, SQLException {
        super.handle(outputStream, request);
        handleUpdate();
    }
}