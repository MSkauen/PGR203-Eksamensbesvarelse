package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.MemberDao;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class AddMemberController implements HttpController {
    private final MemberDao dao;
    public AddMemberController(MemberDao memberDao) {
        this.dao = memberDao;
    }

    @Override
    public void handle(String requestPath, OutputStream outputStream, Map<String, String> query) throws SQLException {
        addMember(query);
    }

    public void addMember(Map<String, String> query) throws SQLException {

        //Gets data from POST-request hashMap
        String name = query.get("name");
        int age = Integer.parseInt(query.get("age"));

        //Creates new member object from POST-request data
        Member member = new Member();
        member.setName(name);
        member.setAge(age);

        //Inserts member in database
        dao.insert(member);
    }

}
