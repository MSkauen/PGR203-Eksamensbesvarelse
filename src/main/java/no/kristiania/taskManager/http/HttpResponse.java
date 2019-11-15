package no.kristiania.taskManager.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    protected enum statusCodes {
            //PUT THE DIFFERENT STATUSCODES HERE
    }

    private HttpServerRequest request;
    private int statusCode;
    private String requestMethod;
    private Map<String, String> headers;

    public HttpResponse(HttpServerRequest request, int statusCode){
        this.request = request;
        this.statusCode = statusCode;
    }

}
