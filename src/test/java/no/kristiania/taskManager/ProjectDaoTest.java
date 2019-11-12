package no.kristiania.taskManager;

import no.kristiania.taskManager.jdbc.Project;
import no.kristiania.taskManager.jdbc.ProjectDao;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectDaoTest {

    private DataSource dataSource = TestDatabase.testDataSource();

    @Test
    void shouldFindSavedProjects() throws SQLException {

        Project project = new Project();
        project.setName("Test");
        ProjectDao dao = new ProjectDao(dataSource);

        dao.insert(project);
        assertThat(dao.listAll()).contains(project);

    }
}
