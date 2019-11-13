package no.kristiania.taskManager.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class EchoHttpController implements HttpController {
    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws IOException {

        String statusCode = query.getOrDefault("status", "200");
        String contentType = query.getOrDefault("content-type", "text/plain");
        String location = query.get("location");
        String body = query.getOrDefault("body", "Hello World");

        int contentLength = body.length();

        outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                 "Content-type: " + contentType + "\r\n" +
                 "Content-length: " + contentLength + "\r\n" +
                 (location != null ? "Location: " + location + "\r\n" : "") +
                 "Connection: close \r\n" +
                 "\r\n" + body).getBytes());
    }

}
