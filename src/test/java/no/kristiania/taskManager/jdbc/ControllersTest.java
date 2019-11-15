package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.controllers.AddMemberController;
import no.kristiania.taskManager.controllers.ListMembersController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllersTest {

    private MemberDao dao = new MemberDao(TestDatabase.testDataSource());

    @Test
    void shouldReturnMembersFromDatabase() throws SQLException {
        Member member1 = MemberDaoTest.sampleMember();
        Member member2 = MemberDaoTest.sampleMember();

        dao.insert(member1);
        dao.insert(member2);


        ListMembersController controller = new ListMembersController(dao);
        assertThat(controller.getBody())
                    .contains(String.format("<option value='%s' id='%s'>%s</option>", member1.getId(), member1.getId(), member1.getName()))
                    .contains(String.format("<option value='%s' id='%s'>%s</option>", member2.getId(), member2.getId(), member2.getName()));
    }

    @Test
    void shouldInsertMembersToDatabase() throws SQLException {
        Member member1 = MemberDaoTest.sampleMember();

        Map<String, String> data = getMemberDataMap(member1);

        AddMemberController controller = new AddMemberController(dao);
        controller.insertData(data);

        assertThat(dao.listAll())
                .contains(member1);

    }

    private Map<String, String> getMemberDataMap(Member member1) {

        Map<String, String> data = new HashMap<>();
        data.put("name", member1.getName());
        data.put("age", Integer.toString(member1.getAge()));

        return data;
    }

}