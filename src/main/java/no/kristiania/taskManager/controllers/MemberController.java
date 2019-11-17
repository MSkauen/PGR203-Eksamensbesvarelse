package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.MemberDao;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberController extends AbstractDaoController<MemberDao> {
    public MemberController(MemberDao o) {
        super(o);
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

    public String getBody() throws SQLException {
        return dao.listAll().stream()
                .map(p -> String.format("<option value='%s' id='%s'>%s</option>", p.getId(), p.getId(), p.getName()))
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
