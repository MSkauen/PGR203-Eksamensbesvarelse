package no.kristiania.taskManager.http;

import no.kristiania.taskManager.http.HttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    private final String URLECHO = "urlecho.appspot.com";

    @Test
    void shouldExecuteHttpRequest() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo");
        assertEquals(200, client.executeGet().getStatusCode());
    }

    @Test
    void shouldReadStatusCode() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo?status=401");
        assertEquals(401, client.executeGet().getStatusCode());
    }

    @Test
    void shouldReadHeaders() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo?content-type=text/plain");
        assertEquals("text/plain; charset=utf-8", client.executeGet().getHeader("Content-type"));
    }

    @Test
    void shouldReadContentLength() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo?body=Hello+world!");
        assertEquals(12, client.executeGet().getContentLength());
    }

    @Test
    void shouldReadBody() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo?body=Hello+world!");
        assertEquals("Hello world!", client.executeGet().getBody());
    }

}
