package no.kristiania.taskManager.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class HttpServer {
    private ServerSocket serverSocket;
    private String assetRoot;


    private HttpController defaultController = new FileHttpController(this);

    private Map<String, HttpController> controllers = new HashMap<>();

    public HttpServer(int port) throws IOException {
        //Opens up a socket, so that we have somewhere to send and receive data.
        serverSocket = new ServerSocket(port);
        controllers.put("/echo", new EchoHttpController());
    }

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = new HttpServer(8080);
        httpServer.setAssetRoot("src/main/resources/taskManager");
        httpServer.start();

    }

    public void start() {
        new Thread(this::run).start();
    }

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private void run() {
        while(true) {
            try {
                //The accept method is used to accept incoming requests to the socket.
                Socket socket = serverSocket.accept();
                HttpServerRequest request = new HttpServerRequest(socket.getInputStream());
                String requestLine = request.getStartLine();
                logger.info("Handling request{}", requestLine);

                String requestTarget = requestLine.split(" ")[1];//OK
                int questionPos = requestTarget.indexOf('?');
                String requestPath = questionPos == -1 ? requestTarget : requestTarget.substring(0, questionPos);
                Map<String, String> query = parseRequestParameters(requestTarget);

                controllers
                        .getOrDefault(requestPath, defaultController)
                        .handle(requestPath, socket.getOutputStream(), query);

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

    public void setAssetRoot(String assetRoot) {
        this.assetRoot = assetRoot;
    }

    public String getAssetRoot(){
        return assetRoot;
    }

    public void addController(String requestPath, HttpController controller) {
        controllers.put(requestPath, controller);
    }
}
