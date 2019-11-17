package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.jdbc.MemberDao;
import no.kristiania.taskManager.jdbc.Membership;
import no.kristiania.taskManager.jdbc.MembershipDao;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class AddMembershipController extends MembershipController {

    public AddMembershipController(MembershipDao o, MemberDao memberDao, TaskDao taskDao) {
        super(o, memberDao, taskDao);
    }

    @Override
    public void handle(OutputStream outputStream, HttpRequest request) throws IOException, SQLException {
        super.handle(outputStream, request);
        handleAdd();
    }
}
