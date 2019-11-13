package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.http.HttpController;

import java.io.IOException;
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
        String name = query.get("name");
        int age = Integer.parseInt(query.get("age"));
        Member member = new Member();
        member.setName(name);
        member.setAge(age);

        dao.insert(member);
    }

}
