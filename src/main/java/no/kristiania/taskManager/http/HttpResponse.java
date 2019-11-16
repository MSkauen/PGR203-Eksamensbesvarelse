package no.kristiania.taskManager.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {

    public enum STATUS_CODE {
        OK(200, "OK"),
        FOUND(302, "FOUND"),
        BAD_REQUEST(400, "BAD REQUEST"),
        UNAUTHORISED(401, "UNAUTHORISED"),
        NOT_FOUND(404, "NOT FOUND"),
        INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

        public int code;
        public String status;

        STATUS_CODE(int code, String status){
            this.code = code;
            this.status = status;
        }
    }

    private OutputStream outputStream;
    private String body = "";
    private Map<String, String> headers;

    public HttpResponse(HttpServerRequest request, OutputStream outputStream){
        this.outputStream = outputStream;
        headers = request.parseRequestParameters();
        headers.putIfAbsent("status", "200");
    }

    private void alterHeaderTable() {
        if(headers.containsKey("body")){
            setBody(headers.get("body"));
            headers.remove("body");
        }

        headers.remove("status");
        headers.putIfAbsent("Content-type", "text/plain");
        headers.putIfAbsent("Content-length", Integer.toString(body.length()));
    }

    private String responseString(STATUS_CODE status_code){
        alterHeaderTable();

        StringBuilder responseString = new StringBuilder();

        responseString.append(
                "HTTP:/1.1 " + status_code.code + " " + status_code.status + "\r\n"
        );

        for(Map.Entry header : headers.entrySet()){
            responseString.append(
                    header.getKey() + ": " + header.getValue() + "\r\n"
            );
        }
        responseString.append("Connection: close\r\n"+"\r\n" + body);

        System.out.println(responseString.toString());
        return responseString.toString();


    }

    public void executeResponse(STATUS_CODE status_code) throws IOException {
        outputStream.write(responseString(status_code).getBytes());
    }

    public void setBody(String body){
        this.body = body;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key){
        return headers.get(key);
    }
}
