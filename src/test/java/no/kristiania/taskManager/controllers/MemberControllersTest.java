package no.kristiania.taskManager.controllers;

import no.kristiania.taskManager.controllers.MemberController;
import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.MemberDao;
import no.kristiania.taskManager.jdbc.MemberDaoTest;
import no.kristiania.taskManager.jdbc.TestDatabase;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberControllersTest {

    private MemberDao dao = new MemberDao(TestDatabase.testDataSource());

    @Test
    void shouldReturnMembersFromDatabase() throws SQLException {
        Member member1 = MemberDaoTest.sampleMember();
        Member member2 = MemberDaoTest.sampleMember();

        dao.insert(member1);
        dao.insert(member2);

        MemberController controller = new MemberController(dao);
        assertThat(controller.getBody("option"))
                .contains(String.format("<option value='%s' id='%s'>NAME: %s | AGE: %s </option>", member1.getId(), member1.getId(), member1.getName(), member1.getAge()))
                .contains(String.format("<option value='%s' id='%s'>NAME: %s | AGE: %s </option>", member2.getId(), member2.getId(), member2.getName(), member2.getAge()));
    }

    @Test
    void shouldInsertMembersToDatabase() throws SQLException {
        Member member1 = MemberDaoTest.sampleMember();

        Map<String, String> data = getMemberDataMap(member1);

        MemberController controller = new MemberController(dao);
        controller.insertData(data);

        assertThat(dao.listAll())
                .contains(member1);

    }

    @Test
    void shouldAlterExistingMember() throws SQLException {
        Member member1 = MemberDaoTest.sampleMember();
        Member member2 = MemberDaoTest.sampleMember();

        dao.insert(member1);
        dao.insert(member2);

        MemberController controller = new MemberController(dao);
        controller.alterData(getDataMapForAltering(member1, member2));

        assertEquals(dao.retrieve(member1.getId()).getName(), dao.retrieve(member2.getId()).getName());
        assertEquals(dao.retrieve(member1.getId()).getAge(), dao.retrieve(member2.getId()).getAge());
    }

    @Test
    private Map<String, String> getMemberDataMap(Member member1) {

        Map<String, String> data = new HashMap<>();
        data.put("name", member1.getName());
        data.put("age", Integer.toString(member1.getAge()));

        return data;
    }

    private Map<String, String> getDataMapForAltering(Member member1, Member member2) {

        Map<String, String> data = new HashMap<>();
        data.put("id", Long.toString(member1.getId()));
        data.put("name", member2.getName());
        data.put("age", Integer.toString(member2.getAge()));

        return data;
    }
}
