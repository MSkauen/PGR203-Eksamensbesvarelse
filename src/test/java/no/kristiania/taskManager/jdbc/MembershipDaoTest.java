package no.kristiania.taskManager.jdbc;


import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static no.kristiania.taskManager.jdbc.MemberDaoTest.sampleMember;
import static no.kristiania.taskManager.jdbc.TaskDaoTest.sampleTask;
import static org.assertj.core.api.Assertions.assertThat;

public class MembershipDaoTest {

    private MembershipDao membershipDao;
    private JdbcDataSource dataSource = TestDatabase.testDataSource();
    private Member member = sampleMember();
    private Task task = sampleTask();


    @BeforeEach
    void setUp() throws SQLException {
        membershipDao = new MembershipDao(dataSource);
        MemberDao memberDao = new MemberDao(dataSource);
        TaskDao taskDao = new TaskDao(dataSource);

        memberDao.insert(member);
        taskDao.insert(task);
    }

    @Test
    void shouldGetInsertedMembership() throws SQLException {
        Membership membership = new Membership();
        membership.setMemberId(member.getId());
        membership.setTaskId(task.getId());

        membershipDao.insert(membership);

        assertThat(membership).hasNoNullFieldsOrProperties();
        assertThat(membershipDao.retrieve(membership.getId()))
                .isEqualToComparingFieldByField(membership);
    }

}
