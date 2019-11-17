package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.jdbc.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MembershipController extends AbstractDaoController<MembershipDao> {
    protected MemberDao memberDao;
    protected TaskDao taskDao;
    protected Map<String, String> query;
    private List<Membership> list;

    protected MembershipController(MembershipDao o, MemberDao memberDao, TaskDao taskDao){
        super(o);
        this.memberDao = memberDao;
        this.taskDao = taskDao;
    }

    @Override
    public String getBody(String htmlObject) throws SQLException {

        if (query.get("memberId") != null) {
            return getTasks(htmlObject);
        } else if (query.get("taskId") != null) {
            return getMembers(htmlObject);
        } else {
            throw new SQLException();
        }
    }

    private String getTasks(String htmlObject) throws SQLException {
        List<Task> taskList = new ArrayList<>();

        list = dao.listTasksByMemberId(Long.parseLong(query.get("memberId"))); //List of tasks owned by a member

        for (Membership membership : list) {
            taskList.add(taskDao.retrieve(membership.getTaskId())); //Adds tasks from tasks table to own list
        }

        if (taskList.isEmpty()) {
            return "There are no tasks assigned to this member <br> Back to http://localhost:8080/index.html";
        }

        return taskList.stream()
                .map(p -> String.format("<%s id='%s'>%s</%s>", htmlObject, p.getId(), p.getName(), htmlObject))
                .collect(Collectors.joining("")); //Parses this to list being shown in browser
    }

    private String getMembers(String htmlObject) throws SQLException {
        list = dao.listMembersByTaskId(Long.parseLong(query.get("taskId"))); //List of tasks owned by a member

        List<Member> memberList = new ArrayList<>();
        for (Membership membership : list) {
            memberList.add(memberDao.retrieve(membership.getMemberId()));
        }

        if (memberList.isEmpty()) {
            return "There are no tasks assigned to this member <br> Back to http://localhost:8080/index.html";
        }

        return memberList.stream()
                .map(p -> String.format("<%s id='%s'>%s</%s>",htmlObject, p.getId(), p.getName(), htmlObject))
                .collect(Collectors.joining(""));
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
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

    @Override
    public void alterData(Map<String, String> requestBodyParameters) {
        //ADD DROPPING TABLE HERE
    }
}
