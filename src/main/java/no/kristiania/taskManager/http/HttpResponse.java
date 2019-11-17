package no.kristiania.taskManager.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpResponse extends HttpMessage {

    public HttpResponse(HttpRequest request, OutputStream outputStream) {
        super(request, outputStream);
    }

    public void executeResponse(STATUS_CODE status_code) throws IOException {
        outputStream.write(responseString(status_code).getBytes(StandardCharsets.UTF_8));
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
            We need to make the header hashmap into only actual headers, so we remove status and body from it.
            We also need to make sure the Content-length header is present.
        */
        if (headers.containsKey("body")) {
            setBody(headers.get("body"));
            headers.remove("body");
        }

        headers.remove("status");
        headers.putIfAbsent("Content-length", Integer.toString(body.length()));
    }


}
