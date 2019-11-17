package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.STATUS_CODE;
import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.MemberDao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberController extends AbstractDaoController<MemberDao> implements HttpController {
    public MemberController(MemberDao o) {
        super(o);
    }

    @Override
    public void handle(OutputStream outputStream, HttpRequest request) throws IOException {
        super.outputStream = outputStream;
        super.request = request;
        super.handle();

        switch (request.getRequestTarget()) {
            case "/api/members?/listMembers=Option":
                handleList("option");
                break;
            case "/api/members?/listMembers=Li":
                handleList("li");
                break;
            case "/api/members?/addMember":
                System.out.println("I got here");
                handleAdd();
                break;
            case "/api/members?/updateMember":
                System.out.println("I got here");
                handleUpdate();
            default:
                response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }
    }

    public void insertData(Map<String, String> requestBodyParameters) throws SQLException {
        String name;
        int age;

        if (requestBodyParameters.containsKey("name") && requestBodyParameters.containsKey("age")) {
            name = requestBodyParameters.get("name");
            age = Integer.parseInt(requestBodyParameters.get("age"));
        } else {
            throw new IllegalArgumentException();
        }

        //Creates new member object from POST-request data
        Member member = new Member();
        member.setName(name);
        member.setAge(age);

        //Inserts member in database
        dao.insert(member);
    }

    public String getBody(String htmlObject) throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<%s value='%s' id='%s'>%s</%s>", htmlObject, p.getId(), p.getId(), p.getName(), htmlObject))
                .collect(Collectors.joining(""));
    }

    @Override
    public void alterData(Map<String, String> requestBodyParameters) throws SQLException {

        System.out.println(requestBodyParameters);
        if (requestBodyParameters.containsKey("id") && requestBodyParameters.containsKey("name") && requestBodyParameters.containsKey("age")) {
            if (!(requestBodyParameters.get("name").isBlank())) {
                dao.update(requestBodyParameters.get("name"), Long.parseLong(requestBodyParameters.get("id")));
            }
            if (!(requestBodyParameters.get("age").isBlank())) {
                dao.update(Integer.parseInt(requestBodyParameters.get("age")), Long.parseLong(requestBodyParameters.get("id")));
            }
        } else {
            throw new IllegalArgumentException();
        }

    }
}
