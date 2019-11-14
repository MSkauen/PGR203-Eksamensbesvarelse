package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.TaskMember;
import no.kristiania.taskManager.jdbc.TaskMemberDao;

import java.sql.SQLException;
import java.util.Map;

public class AddMemberToTaskController extends AbstractAddController<TaskMemberDao> {


    public AddMemberToTaskController(TaskMemberDao o) {
        super(o);
    }

    @Override
    public void insertData(Map<String, String> query) throws SQLException {
        long memberId = Long.parseLong(query.get("memberId"));
        long projectId = Long.parseLong(query.get("projectId"));

        TaskMember taskMember = new TaskMember();
        taskMember.setMemberId(memberId);
        taskMember.setProjectId(projectId);

        dao.insert(taskMember);
    }
}
