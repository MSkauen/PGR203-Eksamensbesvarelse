package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.TaskMember;
import no.kristiania.taskManager.jdbc.TaskMemberDao;

import java.sql.SQLException;
import java.util.Map;

public class MemberToTaskController extends AbstractAddController {
    private final TaskMemberDao taskMemberDao;

    public MemberToTaskController(TaskMemberDao taskMemberDao) {
        this.taskMemberDao = taskMemberDao;

    }

    @Override
    public void insertData(Map<String, String> query) throws SQLException {
        long memberId = Long.parseLong(query.get("memberId"));
        long projectId = Long.parseLong(query.get("projectId"));

        TaskMember taskMember = new TaskMember();
        taskMember.setMemberId(memberId);
        taskMember.setProjectId(projectId);

        taskMemberDao.insert(taskMember);
    }
}
