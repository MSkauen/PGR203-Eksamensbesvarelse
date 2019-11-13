package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.MemberDaoTest;
import no.kristiania.taskManager.TestDatabase;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class MembersControllerTest {



    @Test
    void shouldReturnMembersFromDatabase() throws SQLException {
        MemberDao dao = new MemberDao(TestDatabase.testDataSource());
        Member member1 = MemberDaoTest.sampleMember();
        Member member2 = MemberDaoTest.sampleMember();

        dao.insert(member1);
        dao.insert(member2);

        MembersController controller = new MembersController(dao);
        assertThat(controller.getBody())
                    .contains(String.format("<option id='%s'>%s</option>", member1.getId(), member1.getName(), member1.getAge()))
                    .contains(String.format("<option id='%s'>%s</option>", member2.getId(), member2.getName(), member2.getAge()));
    }
}