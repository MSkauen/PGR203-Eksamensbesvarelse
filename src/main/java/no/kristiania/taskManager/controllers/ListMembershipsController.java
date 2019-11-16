package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpResponse;
import no.kristiania.taskManager.http.HttpServerRequest;
import no.kristiania.taskManager.jdbc.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListMembershipsController extends AbstractListController<MembershipDao> {


    private MemberDao memberDao;
    private TaskDao taskDao;
    private Map<String, String> query;
    private List<Membership> list;

    public ListMembershipsController(MembershipDao o, MemberDao memberDao, TaskDao taskDao) {
        super(o);
        this.memberDao = memberDao;
        this.taskDao = taskDao;
    }


    public void handle(OutputStream outputStream, HttpServerRequest request) throws IOException, SQLException {
        super.handle(outputStream, request);
        query = request.parseRequestBody(request.getBody());
    }

    @Override
    public String getBody() throws SQLException {

        if (query.get("memberId") != null) {
            return getTasks();
        } else if (query.get("taskId") != null) {
            return getMembers();
        } else {
            throw new SQLException();
        }
    }

    private String getTasks() throws SQLException {
        List<Task> taskList = new ArrayList<>();

        list = dao.listTasksByMemberId(Long.parseLong(query.get("memberId"))); //List of tasks owned by a member

        for (Membership membership : list) {
            taskList.add(taskDao.retrieve(membership.getTaskId())); //Adds tasks from tasks table to own list
        }

        if (taskList.isEmpty()) {
            return "There are no tasks assigned to this member <br> Back to http://localhost:8080/index.html";
        }

        return taskList.stream()
                .map(p -> String.format("<li id='%s'>%s</li>", p.getId(), p.getName()))
                .collect(Collectors.joining("")); //Parses this to list being shown in browser
    }

    private String getMembers() throws SQLException {
        list = dao.listMembersByTaskId(Long.parseLong(query.get("taskId"))); //List of tasks owned by a member

        List<Member> memberList = new ArrayList<>();
        for (Membership membership : list) {
            memberList.add(memberDao.retrieve(membership.getMemberId()));
        }

        if (memberList.isEmpty()) {
            return "There are no tasks assigned to this member <br> Back to http://localhost:8080/index.html";
        }

        return memberList.stream()
                .map(p -> String.format("<li id='%s'>%s</li>", p.getId(), p.getName()))
                .collect(Collectors.joining(""));
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

}
