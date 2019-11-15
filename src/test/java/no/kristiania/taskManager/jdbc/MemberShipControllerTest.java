package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.controllers.ListMembershipsController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberShipControllerTest {

    private TaskDao taskDao = new TaskDao(TestDatabase.testDataSource());
    private MemberDao memberDao = new MemberDao(TestDatabase.testDataSource());
    private MembershipDao memberShipDao = new MembershipDao(TestDatabase.testDataSource());

    @Test
    void shouldReturnMembershipFromDatabase() throws SQLException {
        Member member1 = MemberDaoTest.sampleMember();
        Task task1 = TaskDaoTest.sampleTask();

        memberDao.insert(member1);
        taskDao.insert(task1);

        Membership membership = new Membership();
        membership.setMemberId(member1.getId());
        membership.setTaskId(task1.getId());

        memberShipDao.insert(membership);

        System.out.println(membership);

        ListMembershipsController controller = new ListMembershipsController(memberShipDao, memberDao, taskDao);
        controller.setQuery(getDataMap(task1));
        assertThat(controller.getBody())
                .contains(String.format("<li id='%s'>%s</li>", member1.getId(), member1.getName()));

        controller.setQuery(getDataMap(member1));
        assertThat(controller.getBody())
                .contains(String.format("<li id='%s'>%s</li>", task1.getId(), task1.getName()));

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

}
