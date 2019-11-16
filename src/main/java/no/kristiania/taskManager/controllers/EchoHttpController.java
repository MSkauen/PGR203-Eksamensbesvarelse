package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpServerRequest;
import no.kristiania.taskManager.http.STATUS_CODE;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class EchoHttpController implements HttpController {

    private Map<String, String> requestHeaderParameters;

    @Override
    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException {
        HttpResponse response = new HttpResponse(request, outputStream);
        STATUS_CODE status = STATUS_CODE.getCode(Integer.parseInt(response.getHeader("status")));
        response.executeResponse(status);
    }

}
