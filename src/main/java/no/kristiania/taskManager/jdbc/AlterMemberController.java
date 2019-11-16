package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.controllers.AbstractAlterController;

import java.sql.SQLException;
import java.util.Map;

public class AlterMemberController extends AbstractAlterController<MemberDao> {

    public AlterMemberController(MemberDao o){ super(o);}

    @Override
    protected void alterData(Map<String, String> requestBodyParameters) throws SQLException {

        if(requestBodyParameters.containsKey("id") && requestBodyParameters.containsKey("name")){
            dao.alter(requestBodyParameters.get("name"), Long.parseLong(requestBodyParameters.get("id")));
        }else {
            throw new IllegalArgumentException();
        }

    }
}
