package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.MemberDao;
import no.kristiania.taskManager.jdbc.Membership;
import no.kristiania.taskManager.jdbc.MembershipDao;
import no.kristiania.taskManager.jdbc.TaskDao;

import java.sql.SQLException;
import java.util.Map;

public class AddMembershipController extends AbstractAddController<MembershipDao> {

    private MemberDao memberDao;
    private TaskDao taskDao;

    public AddMembershipController(MembershipDao o, MemberDao memberDao, TaskDao taskDao) {
        super(o);
        this.memberDao = memberDao;
        this.taskDao = taskDao;
    }

    @Override
    public void insertData(Map<String, String> query) throws SQLException {
        long memberId = Long.parseLong(query.get("memberId"));
        long taskId = Long.parseLong(query.get("taskId"));

        Membership membership = new Membership();
        membership.setMemberId(memberId);
        membership.setTaskId(taskId);

        dao.insert(membership);
    }
}
