package no.kristiania.taskManager.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient extends HttpMessage {
    private final String hostname;
    private final int port;
    private final String requestTarget;
    private String body = "";

    public HttpClient(String hostname, int port, String requestTarget, String body) {
        super();
        this.hostname = hostname;
        this.port = port;
        this.requestTarget = requestTarget;
        this.body = body;
    }

    public HttpClient(String hostname, int port, String requestTarget) {
        super();
        this.hostname = hostname;
        this.port = port;
        this.requestTarget = requestTarget;
    }

    public HttpClientResponse executePost() throws IOException {
        Socket socket = new Socket(hostname, port);
        String request = "POST "  + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n" +
                "Content-length: " + body.length() + "\r\n" +
                "\r\n" + body;

        System.out.println(request);
        socket.getOutputStream().write(request.getBytes());

        socket.getOutputStream().flush();

        return new HttpClientResponse(socket.getInputStream());
    }

    public HttpClientResponse executeGet() throws IOException {
        Socket socket = new Socket(hostname, port);
        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n\r\n";

        socket.getOutputStream().write(request.getBytes());
        socket.getOutputStream().flush();

        return new HttpClientResponse(socket.getInputStream());
    }
}