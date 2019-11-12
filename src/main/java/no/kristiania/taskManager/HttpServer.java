package no.kristiania.taskManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class HttpServer {
    private ServerSocket serverSocket;
    private String fileLocation;

    public HttpServer(int port) throws IOException {
        //Opens up a socket, so that we have somewhere to send and receive data.
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = new HttpServer(8080);
        httpServer.setFileLocation("src/main/resources");
        httpServer.start();

    }

    void start() {
        new Thread(this::run).start();
    }

    private void run() {
        while (true) {
            try {
                //The accept method is used to accept incoming requests to the socket.
                Socket socket = serverSocket.accept();
                HttpServerRequest request = new HttpServerRequest(socket.getInputStream());
                String requestLine = request.getStartLine();


                String requestTarget = requestLine.split(" ")[1];
                Map<String, String> requestParameters = parseRequestParameters(requestTarget);

                String statusCode = requestParameters.getOrDefault("status", "200");
                String location = requestParameters.get("location");
                String body = requestParameters.getOrDefault("body", "Hello World");

                int questionPos = requestTarget.indexOf("?");

                String requestPath = questionPos == -1 ? requestTarget : requestTarget.substring(0, questionPos);

                //If /echo isn't in the requestPath, return a file from the specified file location
                if (!requestPath.equals("/echo")) {
                    File file = new File(fileLocation + requestPath);
                    socket.getOutputStream().write(("HTTP:/1.1 200 OK\r\n" +
                            "Content-length: " + file.length() + "\r\n" +
                            "Connection: close \r\n" +
                            "\r\n").getBytes());

                    new FileInputStream(file).transferTo(socket.getOutputStream());
                }
                //Writes Hello World
                socket.getOutputStream().write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                        "Content-length: " + body.length() + "\r\n" +
                        (location != null ? "Location: " + location + "\r\n" : "") +
                        "\r\n" +
                        body).getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Map<String, String> parseRequestParameters(String requestTarget) {
        Map<String, String> requestParameters = new HashMap<>();

        int questionPos = requestTarget.indexOf('?');
        if (questionPos != -1) {

            String query = requestTarget.substring(questionPos + 1);

            for (String parameter : query.split("&")) {

                int equalsPos = parameter.indexOf('=');
                String parameterValue = parameter.substring(equalsPos + 1);
                String parameterName = parameter.substring(0, equalsPos);
                requestParameters.put(parameterName, parameterValue);

            }
        }
        return requestParameters;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
