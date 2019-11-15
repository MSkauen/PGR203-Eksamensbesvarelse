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
        this.requestBodyParameters = request.parsePostRequestBody(request.getBody());

        try {
            insertData(requestBodyParameters);

        } catch (PSQLException p) {
            outputStream.write(("HTTP:/1.1 500 Internal server error\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Content-length: 0\r\n" +
                    "Connection: close \r\n" +
                    "\r\n").getBytes());
        }
        outputStream.write(("HTTP:/1.1 200 OK\r\n" +
                "Content-type: text/plain\r\n" +
                "Content-length: 0\r\n" +
                "Connection: close \r\n" +
                "\r\n").getBytes());


    }

    public abstract void insertData(Map<String, String> query) throws SQLException;
}
