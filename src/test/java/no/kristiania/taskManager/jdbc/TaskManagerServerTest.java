package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.http.HttpClientRequest;
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
        HttpClientRequest client = new HttpClientRequest("localhost", server.getPort(), "/api/addMember", "name=Guro&age=24");
        assertEquals(302, client.executePost().getStatusCode());
    }

    @Test
    void shouldHandleListRequest() throws IOException {
        HttpClientRequest client = new HttpClientRequest("localhost", server.getPort(), "/api/listMembers");
        assertEquals(200, client.executeGet().getStatusCode());
    }

    @Test
    void shouldReturnInternalServerError() throws IOException {
        HttpClientRequest client = new HttpClientRequest("localhost", server.getPort(), "/api/addMember", "foo=bar&bar=foo");
        assertEquals(500, client.executePost().getStatusCode());
    }


}
