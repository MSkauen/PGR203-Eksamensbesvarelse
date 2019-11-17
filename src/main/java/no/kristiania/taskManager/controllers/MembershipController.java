package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.http.HttpRequest;
import no.kristiania.taskManager.http.STATUS_CODE;
import no.kristiania.taskManager.jdbc.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MembershipController extends AbstractDaoController<MembershipDao> implements HttpController {
    protected MemberDao memberDao;
    protected TaskDao taskDao;
    protected Map<String, String> query;
    private List<Membership> list;

    public MembershipController(MembershipDao o, MemberDao memberDao, TaskDao taskDao){
        super(o);
        this.memberDao = memberDao;
        this.taskDao = taskDao;
    }


    @Override
    public void handle(OutputStream outputStream, HttpRequest request) throws IOException {
        super.outputStream = outputStream;
        super.request = request;
        query = request.parseRequestBody(request.getBody());
        super.handle();

        switch (request.getRequestTarget()) {
            case "/api/memberships?/listMemberShips=Option":
                handleList("option");
                break;
            case "/api/memberships?/listMemberships=Li":
                handleList("li");
                break;
            case "/api/memberships?/addMembership":
                handleAdd();
                break;
            default:
                response.executeResponse(STATUS_CODE.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getBody(String htmlObject) throws SQLException {

        if (query.get("memberId") != null) {
            return getTasks(htmlObject);
        } else if (query.get("taskId") != null) {
            System.out.println("I got here");
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
            return "There are no tasks assigned to this member <br> Back to <a href='http://localhost:8080/Members/index.html'>Back to members</a>";
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
            return "There are no tasks assigned to this member <br> Back to <a href='http://localhost:8080/Tasks/index.html'>Back to tasks</a>";
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
    void alterData(Map<String, String> requestBodyParameters) {
    }
}
