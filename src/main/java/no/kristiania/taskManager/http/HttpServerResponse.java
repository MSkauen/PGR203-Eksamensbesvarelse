package no.kristiania.taskManager.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpServerResponse {

    private OutputStream outputStream;
    private String body = "";
    private Map<String, String> headers;

    public HttpServerResponse(HttpServerRequest request, OutputStream outputStream) {
        this.outputStream = outputStream;
        headers = request.parseRequestParameters();
        headers.putIfAbsent("status", "200");
    }

    public void executeResponse(STATUS_CODE status_code) throws IOException {
        outputStream.write(responseString(status_code).getBytes());
    }

    private String responseString(STATUS_CODE status_code) {

        alterHeaderTable();

        StringBuilder responseString = new StringBuilder();
        responseString.append("HTTP:/1.1 ").append(status_code.code).append(" ").append(status_code.status).append("\r\n");
        for (Map.Entry header : headers.entrySet()) {
            responseString.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        responseString.append("Connection: close\r\n\r\n").append(body);

        return responseString.toString();

    }

    private void alterHeaderTable() {
        /*
            We need to make the header hashmap into only http-headers, so we remove status and body from it.
            We also need to make sure the Content-length header is present.
        */
        if (headers.containsKey("body")) {
            setBody(headers.get("body"));
            headers.remove("body");
        }

        headers.remove("status");
        headers.putIfAbsent("Content-length", Integer.toString(body.length()));
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
