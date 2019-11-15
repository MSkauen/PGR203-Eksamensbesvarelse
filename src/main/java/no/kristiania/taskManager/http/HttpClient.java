package no.kristiania.taskManager.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient extends HttpMessage {
    private final String hostname;
    private final int port;
    private final String requestTarget;



    public HttpClient(String hostname, int port, String requestTarget) {
        super();
        this.hostname = hostname;
        this.port = port;
        this.requestTarget = requestTarget;
    }

    public HttpClientResponse executeGet() throws IOException {
        Socket socket = new Socket(hostname, port);

        socket.getOutputStream().write(("GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n" +
                "\r\n").getBytes());

        socket.getOutputStream().flush();

        return new HttpClientResponse(socket.getInputStream());
    }
}