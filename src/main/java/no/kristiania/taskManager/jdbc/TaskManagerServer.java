package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.http.HttpServer;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TaskManagerServer {

    private HttpServer server;

    public TaskManagerServer(int port) throws IOException {
        Properties properties = new Properties();
        try(FileReader fileReader = new FileReader("task-manager.properties")) {
            properties.load(fileReader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.user"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();

        server = new HttpServer(port);
        server.setAssetRoot("src/main/resources/taskManager");
        server.addController("/api/members", new MembersController(new MemberDao(dataSource)));
        server.addController("/members", new AddMemberController(new MemberDao(dataSource)));
    }

    public static void main(String[] args) throws IOException {
        new TaskManagerServer(8080).start();
    }

    public void start() {
        server.start();
    }
}
