package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.STATUS_CODE;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractDaoController<DAO> {

    protected DAO dao;
    protected OutputStream outputStream;
    protected Map<String, String> requestBodyParameters;
    protected HttpResponse response;
    protected String name;
    protected HttpRequest request;
    

    protected AbstractDaoController(DAO o) {
        this.dao = o;
    }

    public void handle() {
        response = new HttpResponse(request, outputStream);
    }

    public void handleAdd() throws IOException {
        System.out.println(request.getBody());
        this.requestBodyParameters = request.parseRequestBody(request.getBody());

        try {
            insertData(requestBodyParameters);
            response.setHeader("Location", "http://localhost:8080/index.html");
            response.executeResponse(STATUS_CODE.FOUND);

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }

    }

    public void handleList(String htmlObject) throws IOException {
        response.setHeader("Content-type", "text/html");

        try {
            response.setHeader("Content-length", Integer.toString(getBody(htmlObject).length()));
            response.setBody(getBody(htmlObject));
            response.executeResponse(STATUS_CODE.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setHeader("Content-length", Integer.toString(e.toString().length()));
            response.setBody(e.toString());
            response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }
    }

    public void handleUpdate() throws IOException {
        try {
            alterData(requestBodyParameters);
            response.setHeader("Location", "http://localhost:8080/index.html");
            response.executeResponse(STATUS_CODE.FOUND);

        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }
    }

    abstract String getBody(String htmlObject) throws SQLException;
    abstract void insertData(Map<String, String> query) throws SQLException;
    abstract void alterData(Map<String, String> requestBodyParameters) throws SQLException;
}
