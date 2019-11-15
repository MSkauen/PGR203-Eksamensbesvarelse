package no.kristiania.taskManager.http;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {

    //IN HTTP MESSAGE - implement METHOD and REQUESTLINE instead of startLine
    private String body;
    private String startLine;
    private Map<String, String> headers = new HashMap<>();
    private String requestTarget;

    public HttpMessage() {

    }

    public HttpMessage(InputStream inputStream) throws IOException {
        startLine = readLine(inputStream);
        String headerLine;
        while (!(headerLine = readLine(inputStream)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerName = headerLine.substring(0, colonPos).trim();
            String headerValue = headerLine.substring(colonPos + 1).trim();
            headers.put(headerName.toLowerCase(), headerValue); // Stores the headerName as a key and headerValue as a value
        }
        if (getHeader("Content-length") != null) {
            this.body = readBytes(inputStream, getContentLength());
        }

        requestTarget = getStartLine().split(" ")[1];//OK
    }


    public String getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-length"));
    }

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != -1) {
            if (c == '\r') {
                inputStream.read(); // the following \n
                break;
            }
            line.append((char) c);
        }
        return line.toString();
    }

    private String readBytes(InputStream inputStream, int contentLength) throws IOException {
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            body.append((char) inputStream.read());
        }
        return body.toString();
    }

    public String getStartLine() {
        return startLine;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return Integer.parseInt(startLine.split(" ")[1]);
    }

    public String getRequestTarget() {
        return requestTarget;
    }

}