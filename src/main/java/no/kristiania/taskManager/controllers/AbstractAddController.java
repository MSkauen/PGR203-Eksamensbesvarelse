package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractAddController<ENTITY> implements HttpController{

    protected ENTITY dao;
    protected OutputStream outputStream;

    protected AbstractAddController(ENTITY o) {
        this.dao = o;
    }

    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws SQLException, IOException {
        this.outputStream = outputStream;
        insertData(query);


        //SHOULD WE MAKE A
        HttpResponse response = new HttpResponse();
        outputStream.write(("HTTP:/1.1 200 OK\r\n" +
                "Content-type: text/plain\r\n" +
                "Content-length: 0\r\n" +
                "Connection: close \r\n" +
                "\r\n").getBytes());
        //ADD LOGGER HERE

    }

    public abstract void insertData(Map<String, String> query) throws SQLException;
}
