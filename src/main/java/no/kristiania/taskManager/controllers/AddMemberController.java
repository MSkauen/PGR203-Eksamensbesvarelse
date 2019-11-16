package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.MemberDao;

import java.sql.SQLException;
import java.util.Map;

public class AddMemberController extends AbstractAddController<MemberDao> {

    public AddMemberController(MemberDao o) {
        super(o);
    }

    @Override
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
}
