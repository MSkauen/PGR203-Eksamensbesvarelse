package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpServerRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class EchoHttpController implements HttpController {

    private Map<String, String> requestHeaderParameters;

    @Override
    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException {

        requestHeaderParameters = request.parseRequestParameters();

        String statusCode = requestHeaderParameters.getOrDefault("status", "200");
        String contentType = requestHeaderParameters.getOrDefault("content-type", "text/plain");
        String location = requestHeaderParameters.get("location");
        String body = requestHeaderParameters.getOrDefault("body", "Hello World");

        outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                 "Content-type: " + contentType + "\r\n" +
                 "Content-length: " + body.length() + "\r\n" +
                (location != null ? "Location: " + location + "\r\n" : "") +
                 "Connection: close \r\n" +
                 "\r\n" + body).getBytes());
    }

}
