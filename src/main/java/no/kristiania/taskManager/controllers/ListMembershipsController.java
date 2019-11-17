package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.jdbc.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListMembershipsController extends MembershipController {

    public ListMembershipsController(MembershipDao o, MemberDao memberDao, TaskDao taskDao) {
        super(o, memberDao, taskDao);
    }

    public void handle(OutputStream outputStream, HttpRequest request) throws IOException, SQLException {
        super.handle(outputStream, request);
        setQuery(request.parseRequestBody(request.getBody()));
        handleUpdate();
    }

}
