package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.controllers.MembershipController;
import no.kristiania.taskManager.jdbc.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static no.kristiania.taskManager.jdbc.MemberDaoTest.sampleMember;
import static no.kristiania.taskManager.jdbc.TaskDaoTest.sampleTask;
import static org.assertj.core.api.Assertions.assertThat;

public class MembershipControllerTest {

    private TaskDao taskDao = new TaskDao(TestDatabase.testDataSource());
    private Task task;
    private MemberDao memberDao = new MemberDao(TestDatabase.testDataSource());
    private Member member;
    private MembershipDao memberShipDao = new MembershipDao(TestDatabase.testDataSource());

    @BeforeEach
    void setUp() throws SQLException {
        this.task = sampleTask();
        this.member = sampleMember();
        memberShipDao.insert(sampleMembership(member, task));
    }

    @Test
    void shouldReturnMembersByTaskIdFromDatabase() throws SQLException {
        MembershipController controller = new MembershipController(memberShipDao, memberDao, taskDao);

        controller.setQuery(getDataMap(task));
        assertThat(controller.getBody("li"))
                .contains(String.format("<li id='%s'>%s</li>", member.getId(), member.getName()));
    }

    @Test
    void shouldReturnTasksByMemberIdFromDatabase() throws SQLException {
        MembershipController controller = new MembershipController(memberShipDao, memberDao, taskDao);

        controller.setQuery(getDataMap(member));
        assertThat(controller.getBody("li"))
                .contains(String.format("<li id='%s'>%s</li>", task.getId(), task.getName()));

    }

    private Map<String, String> getDataMap(Task task1) {
        Map<String, String> data = new HashMap<>();
        data.put("taskId", Long.toString(task1.getId()));

        return data;
    }

    private Map<String, String> getDataMap(Member member1) {
        Map<String, String> data = new HashMap<>();
        data.put("memberId", Long.toString(member1.getId()));

        return data;
    }

    public Membership sampleMembership(Member member, Task task) throws SQLException {
        memberDao.insert(member);
        taskDao.insert(task);

        Membership membership = new Membership();
        membership.setMemberId(member.getId());
        membership.setTaskId(task.getId());

        return membership;
    }


}
