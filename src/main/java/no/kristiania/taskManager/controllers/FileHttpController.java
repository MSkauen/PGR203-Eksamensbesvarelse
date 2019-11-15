package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpServer;
import no.kristiania.taskManager.http.HttpServerRequest;

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
        File file = new File(httpServer.getAssetRoot() + request.getRequestTarget());
        if(file.isDirectory()){
            file = new File(file, "taskManager/index.html");
        }
        if(file.exists()){
            long length = file.length();
            outputStream.write(("HTTP:/1.1 200 OK\r\n" +
                    "Content-length: " + length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n").getBytes());
            try (FileInputStream fileInputStream = new FileInputStream(file)){
                fileInputStream.transferTo(outputStream);
            }
        } else {
            outputStream.write(("HTTP/1.1 404 Not found\r\n" +
                    "Connection: close\r\n" +
                    "\r\n").getBytes());
        }
    }

}
