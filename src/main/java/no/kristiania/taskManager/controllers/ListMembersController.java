package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.MemberDao;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;


public class ListMembersController extends AbstractListController<MemberDao> {

    public ListMembersController(MemberDao o) {
        super(o);
    }

    public String getBody() throws SQLException {

        return dao.listAll().stream()
                .map(p -> String.format("<option value='%s' id='%s'>%s</option>", p.getId(), p.getId(), p.getName()))
                .collect(Collectors.joining(""));
    }

}