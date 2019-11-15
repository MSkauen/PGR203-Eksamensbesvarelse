package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpServerRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class EchoHttpController implements HttpController {

    private Map<String, String> requestBodyParameters;

    @Override
    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException {

        requestBodyParameters = request.parsePostRequestBody(request.getBody());

        String statusCode = requestBodyParameters.getOrDefault("status", "200");
        String contentType = requestBodyParameters.getOrDefault("content-type", "text/plain");
        String location = requestBodyParameters.get("location");
        String body = requestBodyParameters.getOrDefault("body", "Hello World");

        int contentLength = body.length();

        outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                 "Content-type: " + contentType + "\r\n" +
                 "Content-length: " + contentLength + "\r\n" +
                 (location != null ? "Location: " + location + "\r\n" : "") +
                 "Connection: close \r\n" +
                 "\r\n" + body).getBytes());
    }

}
