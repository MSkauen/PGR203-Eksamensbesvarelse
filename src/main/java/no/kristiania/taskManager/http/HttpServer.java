package no.kristiania.taskManager.http;

import no.kristiania.taskManager.controllers.EchoHttpController;
import no.kristiania.taskManager.controllers.FileHttpController;
import no.kristiania.taskManager.controllers.HttpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private ServerSocket serverSocket;
    private String assetRoot;

    private HttpController defaultController = new FileHttpController(this);
    private Map<String, HttpController> controllers = new HashMap<>();

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        controllers.put("/echo", new EchoHttpController());
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(8080);
        httpServer.start();
    }

    public void start() {
        new Thread(this::run).start();
    }

    private void run() {
        while (true) {
            try {
                //The accept method is used to accept incoming requests to the socket.
                Socket socket = serverSocket.accept();
                HttpRequest request = new HttpRequest(socket.getInputStream());

                logger.info("Handling request{} ", request.getStartLine());
                controllers
                        .getOrDefault(getAbsolutePath(request), defaultController)
                        .handle(socket.getOutputStream(), request);


            } catch (IOException | NullPointerException | SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void addController(String requestPath, HttpController controller) {
        controllers.put(requestPath, controller);
    }

    private String getAbsolutePath(HttpRequest request) {
        String requestTarget = request.getRequestTarget();

        //If requestPath is /foo?/bar it should return /foo to find the right controller.
        int questionPos = requestTarget.indexOf("?");
        requestTarget = (questionPos == -1) ? requestTarget : requestTarget.substring(0, questionPos);

        return requestTarget;
    }

    /*Getters and setters*/

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public String getAssetRoot() {
        return assetRoot;
    }

    public void setAssetRoot(String assetRoot) {
        this.assetRoot = assetRoot;
    }


}
