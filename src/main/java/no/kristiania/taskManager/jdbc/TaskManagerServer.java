package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.http.HttpServer;

import java.io.IOException;

public class TaskManagerServer {

    private HttpServer server;

    public TaskManagerServer(int port) throws IOException {
        server = new HttpServer(port);
        server.setAssetRoot("src/main/resources/task-manager");

    }

    public static void main(String[] args) throws IOException {
        new TaskManagerServer(8080).start();
    }

    private void start() {
        server.start();
    }
}
