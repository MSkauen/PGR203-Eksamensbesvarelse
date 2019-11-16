package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpServerRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class EchoHttpController implements HttpController {

    private Map<String, String> requestHeaderParameters;

    @Override
    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException {
        HttpResponse response = new HttpResponse(request, outputStream);
        HttpResponse.STATUS_CODE status = getCode(Integer.parseInt(response.getHeader("status")));
        response.executeResponse(status);

        /* outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                 "Content-type: " + contentType + "\r\n" +
                 "Content-length: " + body.length() + "\r\n" +
                (location != null ? "Location: " + location + "\r\n" : "") +
                 "Connection: close \r\n" +
                 "\r\n" + body).getBytes());*/
    }

    HttpResponse.STATUS_CODE getCode(int code){
        for(HttpResponse.STATUS_CODE e : HttpResponse.STATUS_CODE.values()){
            if(e.code == code){
                return e;
            }
        }
        return HttpResponse.STATUS_CODE.INTERNAL_SERVER_ERROR;
    }

}
