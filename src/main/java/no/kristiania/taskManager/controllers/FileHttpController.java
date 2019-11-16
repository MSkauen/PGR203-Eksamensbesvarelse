package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpServer;
import no.kristiania.taskManager.http.HttpServerRequest;
import no.kristiania.taskManager.http.STATUS_CODE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class FileHttpController implements HttpController {
    private HttpServer httpServer;

    public FileHttpController(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException {

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
        } else {
            response.executeResponse(STATUS_CODE.NOT_FOUND);
        }
    }

}
