package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.MemberDao;

import java.sql.SQLException;
import java.util.Map;

public class UpdateMemberController extends AbstractUpdateController<MemberDao> {

    public UpdateMemberController(MemberDao o) {
        super(o);
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