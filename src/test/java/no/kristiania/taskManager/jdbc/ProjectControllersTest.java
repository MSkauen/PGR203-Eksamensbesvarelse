package no.kristiania.taskManager.jdbc;

import no.kristiania.taskManager.controllers.ListProjectsController;
import no.kristiania.taskManager.controllers.ProjectController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectControllersTest {

    private ProjectDao dao = new ProjectDao(TestDatabase.testDataSource());

    @Test
    void shouldReturnProjectFromDatabase() throws SQLException {
        Project project1 = ProjectDaoTest.sampleProject();
        Project project2 = ProjectDaoTest.sampleProject();

        dao.insert(project1);
        dao.insert(project2);

        ListProjectsController controller = new ListProjectsController(dao);
        assertThat(controller.getBody("option"))
                .contains(String.format("<option value='%s' id='%s'>%s</option>", project1.getId(), project1.getId(), project1.getName()));
    }
}
