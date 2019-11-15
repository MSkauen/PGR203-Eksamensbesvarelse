package no.kristiania.taskManager.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpServerTest {

    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpServer(0);
        server.start();
    }

    @Test
    void shouldReturnStatusCode200() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo");
        assertEquals(200, client.executeGet().getStatusCode());

    }

    @Test
    void shouldReturnStatusCode401() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=401");
        assertEquals(401, client.executeGet().getStatusCode());
    }

    @Test
    void shouldReturnHeaders() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=302&location=http://www.example.com");
        HttpClientResponse response = client.executeGet();
        assertEquals(302, response.getStatusCode());
        assertEquals("http://www.example.com", response.getHeader("location"));
    }

    @Test
    void shouldReturnBody() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?body=HelloWorld");
        assertEquals("HelloWorld", client.executeGet().getBody());
    }

    @Test
    void shouldReturnFileFromDisk() throws IOException {
        Files.writeString(Paths.get("target/mytestfile.txt"), "Hello Kristiania");
        server.setAssetRoot("target");
        HttpClient client = new HttpClient("localhost", server.getPort(), "/mytestfile.txt");
        HttpClientResponse response = client.executeGet();
        assertEquals("Hello Kristiania", response.getBody());
    }

    @Test
    void shouldReturn(){

    }

}