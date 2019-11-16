package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpServerRequest;
import no.kristiania.taskManager.http.STATUS_CODE;
import no.kristiania.taskManager.jdbc.MemberDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractAlterController<ENTITY> implements HttpController{

    protected ENTITY dao;

    public AbstractAlterController(ENTITY o) {
        this.dao = o;
    }

    @Override
    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException {
        System.out.println(request.getBody());
        Map<String, String> requestBodyParameters = request.parseRequestBody(request.getBody());
        System.out.println(requestBodyParameters.toString());
        HttpResponse response = new HttpResponse(request, outputStream);

        try {
            alterData(requestBodyParameters);
            response.setHeader("Location", "http://localhost:8080/index.html");
            response.executeResponse(STATUS_CODE.FOUND);

        } catch (IllegalArgumentException | SQLException e)  {
            System.out.println(e);
            response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }

    }

    protected abstract void alterData(Map<String, String> requestBodyParameters) throws SQLException;
}
