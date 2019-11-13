package no.kristiania.taskManager.jdbc;


import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static no.kristiania.taskManager.jdbc.MemberDaoTest.sampleMember;
import static no.kristiania.taskManager.jdbc.ProjectDaoTest.sampleProject;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskMemberDaoTest {

    private TaskMemberDao taskMemberDao;
    private JdbcDataSource dataSource = TestDatabase.testDataSource();
    private Member member = sampleMember();
    private Project project = sampleProject();


    @BeforeEach
    void setUp() throws SQLException {
        taskMemberDao = new TaskMemberDao(dataSource);
        MemberDao memberDao = new MemberDao(dataSource);
        ProjectDao projectDao = new ProjectDao(dataSource);

        memberDao.insert(member);
        projectDao.insert(project);
    }

    @Test
    void shouldGetInsertedMembership() throws SQLException {
        TaskMember taskMember = new TaskMember();
        taskMember.setMemberId(member.getId());
        taskMember.setProjectId(project.getId());

        taskMemberDao.insert(taskMember);

        assertThat(taskMember).hasNoNullFieldsOrProperties();
        assertThat(taskMemberDao.retrieve(taskMember.getId()))
                .isEqualToComparingFieldByField(taskMember);
    }

}
