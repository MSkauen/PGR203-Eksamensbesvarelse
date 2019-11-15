package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpServerRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractListController<ENTITY> implements HttpController {

    protected ENTITY dao;

    public AbstractListController(ENTITY o){
        this.dao = o;
    }

    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException, SQLException {
        try {
            // CLEAN THIS UP
            //METHODS CAN BE EXTRACTED WITHOUT DOUBT
            //
            int statusCode = 200;
            String body = getBody();
            String contentType = "text/html";

            outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                    "Content-type: " + contentType + "\r\n" +
                    "Content-length: " + body.length() + "\r\n" +
                    "Connection: close \r\n" +
                    "\r\n" + body).getBytes());
        } catch (SQLException e) {

            int statusCode = 200;
            String body = e.toString();
            int contentLength = body.length();
            String contentType = "text/html";

            outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                    "Content-type: " + contentType + "\r\n" +
                    "Content-length: " + contentLength + "\r\n" +
                    "Connection: close \r\n" +
                    "\r\n" + body).getBytes());
        }
    }

    protected abstract String getBody() throws SQLException;

}
