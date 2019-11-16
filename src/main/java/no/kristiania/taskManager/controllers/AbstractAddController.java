package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpServerRequest;
import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractAddController<ENTITY> implements HttpController{

    protected ENTITY dao;
    protected OutputStream outputStream;
    protected Map<String, String> requestBodyParameters;
    protected HttpResponse response;

    protected AbstractAddController(ENTITY o) {
        this.dao = o;
    }

    @Override
    public void handle(OutputStream outputStream, HttpServerRequest request) throws SQLException, IOException {
        this.outputStream = outputStream;
        this.requestBodyParameters = request.parseRequestBody(request.getBody());
        response = new HttpResponse(request, outputStream);

        try {
            insertData(requestBodyParameters);

        } catch (PSQLException p) {
            response.executeResponse(HttpResponse.STATUS_CODE.INTERNAL_SERVER_ERROR);
        }

        response.executeResponse(HttpResponse.STATUS_CODE.OK); //should probably make this its own thing

    }

    public abstract void insertData(Map<String, String> query) throws SQLException;
}
