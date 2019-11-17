package no.kristiania.taskManager.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {

    protected OutputStream outputStream;
    protected Map<String, String> headers = new HashMap<>();
    protected String body = "";
    private String startLine;

    public HttpMessage() {

    }

    public HttpMessage(HttpRequest request, OutputStream outputStream){
        this.outputStream = outputStream;
        headers = request.parseRequestParameters();
        headers.putIfAbsent("status", "200");
    }

    public HttpMessage(InputStream inputStream) throws IOException {
        startLine = readLine(inputStream);
        String headerLine;
        while (!(headerLine = readLine(inputStream)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerName = headerLine.substring(0, colonPos).trim();
            String headerValue = headerLine.substring(colonPos + 1).trim();
            headers.put(headerName.toLowerCase(), headerValue);
        }
        if (getHeader("Content-length") != null) {
            this.body = readBytes(inputStream, getContentLength());
        }
    }

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != -1) {
            if (c == '\r') {
                inputStream.read();
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

    public void parseParameter(char splitChar, String message){
        try{
            int charPos = message.indexOf(splitChar);
            String name = message.substring(0, charPos);
            String value = message.substring(charPos + 1);
            headers.put(name, value);
        } catch(StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    /*GETTERS AND SETTERS*/

    public String getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }

    public void setHeader(String key, String value) {
        headers.put(key, value.toLowerCase());
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-length"));
    }

    public String getStartLine() {
        return startLine;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return Integer.parseInt(startLine.split(" ")[1]);
    }

}