package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.MemberDao;

import java.sql.SQLException;
import java.util.Map;

public class AddMemberController extends AbstractAddController<MemberDao> {

    private String name;
    private int age;
    public AddMemberController(MemberDao o) {
        super(o);
    }

    @Override
    public void insertData(Map<String, String> query) throws SQLException {
        //Gets data from POST-request hashMap

        if(query.containsKey("name")){
            name = query.get("name");
        }
        if(query.containsKey("age")){
            age = Integer.parseInt(query.get("age"));
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
