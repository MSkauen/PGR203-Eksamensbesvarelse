package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.STATUS_CODE;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractAddController<ENTITY> implements HttpController {

    protected ENTITY dao;
    protected OutputStream outputStream;
    protected Map<String, String> requestBodyParameters;
    protected HttpResponse response;

    protected AbstractAddController(ENTITY o) {
        this.dao = o;
    }

    @Override
    public void handle(OutputStream outputStream, HttpRequest request) throws IOException {
        this.outputStream = outputStream;
        this.requestBodyParameters = request.parseRequestBody(request.getBody());
        response = new HttpResponse(request, outputStream);

        try {
            insertData(requestBodyParameters);
            response.setHeader("Location", "http://localhost:8080/index.html");
            response.executeResponse(STATUS_CODE.FOUND);

        } catch (SQLException | IllegalArgumentException e) {
            response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }

    }

    public abstract void insertData(Map<String, String> query) throws SQLException;
}
