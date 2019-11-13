package no.kristiania.taskManager;

import no.kristiania.taskManager.jdbc.Member;
import no.kristiania.taskManager.jdbc.MemberDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDaoTest {

    private JdbcDataSource dataSource = TestDatabase.testDataSource();
    private MemberDao dao;

    @BeforeEach
    void setUp(){
        dao = new MemberDao(dataSource);
    }

    @Test
    void shouldListInsertedMember() throws SQLException {
        Member member = sampleMember();

        dao.insert(member);
        assertThat(dao.listAll())
                .extracting(Member::getName)
                .contains(member.getName());
    }

    @Test
    void shouldSaveAllMemberFields() throws SQLException {
        Member member = sampleMember();
        dao.insert(member);

        assertThat(member).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieve(member.getId()))
                .isEqualToComparingFieldByField(member);
    }

    public static Member sampleMember() {
        Member product = new Member();
        product.setName(sampleMemberName());
        product.setAge(randomAge());
        return product;
    }

    private static int randomAge() {
        return new Random().nextInt(100);
    }


    private static String sampleMemberName() {
        String[] members = {"Remi", "Guro", "Christoffer"};
        return members[new Random().nextInt(members.length)];
    }
}
