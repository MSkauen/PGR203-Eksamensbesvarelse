package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpServer;
import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.STATUS_CODE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileHttpController implements HttpController {
    private HttpServer httpServer;

    public FileHttpController(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void handle(OutputStream outputStream, HttpRequest request) throws IOException {

        HttpResponse response = new HttpResponse(request, outputStream);

        File file = new File(httpServer.getAssetRoot() + request.getRequestTarget());
        if (file.isDirectory()) {
            file = new File(file, "taskManager/index.html");
        }
        if (file.exists()) {
            long length = file.length();
            response.setHeader("Content-length", Long.toString(length));
            response.executeResponse(STATUS_CODE.OK);

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.transferTo(outputStream);
            }
        } else if (request.getRequestTarget().equals("/")) {
            response.setHeader("Location", "/index.html");
            response.executeResponse(STATUS_CODE.FOUND);
        } else {
            response.executeResponse(STATUS_CODE.NOT_FOUND);
        }
    }

}
