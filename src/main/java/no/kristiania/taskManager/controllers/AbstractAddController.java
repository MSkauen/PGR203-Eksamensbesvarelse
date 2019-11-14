package no.kristiania.taskManager.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractAddController<ENTITY> implements HttpController{

    protected final ENTITY dao;

    protected AbstractAddController(ENTITY o) {
        this.dao = o;
    }

    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws SQLException, IOException {
        insertData(query);


        //SHOULD WE MAKE A
        outputStream.write(("HTTP:/1.1 200 OK\r\n" +
                "Content-type: text/plain\r\n" +
                "Content-length: 0\r\n" +
                "Connection: close \r\n" +
                "\r\n").getBytes());
        //ADD LOGGER HERE
    }

    public abstract void insertData(Map<String, String> query) throws SQLException;
}
