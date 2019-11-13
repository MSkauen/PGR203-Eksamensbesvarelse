package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.http.HttpController;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class MembersController implements HttpController {

    private final MemberDao dao;
    public MembersController(MemberDao dao) {
        this.dao = dao;
    }

    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws IOException {
        try {
            int statusCode = 200;
            String body = getBody();
            String contentType = "text/html";

            outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                    "Content-type: " + contentType + "\r\n" +
                    "Content-length: " + body.length() + "\r\n" +
                    "Connection: close \r\n" +
                    "\r\n" + body).getBytes());
        } catch (SQLException e) {

            int statusCode = 200;
            String body = e.toString();
            int contentLength = body.length();
            String contentType = "text/html";

            outputStream.write(("HTTP:/1.1 " + statusCode + " OK\r\n" +
                    "Content-type: " + contentType + "\r\n" +
                    "Content-length: " + contentLength + "\r\n" +
                    "Connection: close \r\n" +
                    "\r\n" + body).getBytes());
        }
    }


    String getBody() throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<option id='%s'>%s</option>", p.getId(), p.getName()))
                .collect(Collectors.joining(""));
    }

}
