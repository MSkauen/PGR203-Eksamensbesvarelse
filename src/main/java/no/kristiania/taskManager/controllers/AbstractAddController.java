package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Member;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractAddController implements HttpController{

    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws SQLException, IOException {
        insertData(query);

        outputStream.write(("HTTP:/1.1 200 OK\r\n" +
                "Content-type: text/plain\r\n" +
                "Content-length: 0\r\n" +
                "Connection: close \r\n" +
                "\r\n").getBytes());
        //ADD LOGGER HERE
    }

    public abstract void insertData(Map<String, String> query) throws SQLException;
}
