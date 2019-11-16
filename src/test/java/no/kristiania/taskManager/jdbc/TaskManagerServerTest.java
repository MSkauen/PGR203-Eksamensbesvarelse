package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskManagerServerTest {

    private TaskManagerServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new TaskManagerServer(0, TestDatabase.testDataSource());
        server.start();
    }

    @Test
    void shouldHandlePostRequests() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/members", "name=Guro&age=24");
        assertEquals(302, client.executePost().getStatusCode());
    }

    @Test
    void shouldHandleListRequest() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/members");
        assertEquals(200, client.executeGet().getStatusCode());
    }

    @Test
    void shouldReturnInternalServerError() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/members", "foo=bar&bar=foo");
        assertEquals(500, client.executePost().getStatusCode());
    }



}
