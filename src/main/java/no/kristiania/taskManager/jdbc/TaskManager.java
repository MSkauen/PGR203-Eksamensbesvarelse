package no.kristiania.taskManager.jdbc;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;

public class TaskManager {

    private PGSimpleDataSource dataSource;
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private MemberDao memberDao;
    private ProjectDao projectDao;


    public TaskManager() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("task-manager.properties"));

        dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.user"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();

        memberDao = new MemberDao(dataSource);
        projectDao = new ProjectDao(dataSource);
    }

    public static void main(String[] args) throws IOException, SQLException {
        new TaskManager().run();
    }

    public void run() throws SQLException, IOException {
        System.out.println("Choose action: create [member] | create [project]");
        String action = input.readLine();

        switch (action.toLowerCase()){
            case "member":
                insertMember();
                break;
            case "project":
                insertProject();
                break;
        }
    }

    private void insertProject() throws IOException, SQLException {
        System.out.println("Enter a project you would like to add");
        Project project = new Project();
        project.setName(input.readLine());
        projectDao.insert(project);

        System.out.println(projectDao.listAll());
    }

    private void insertMember() throws IOException, SQLException {
        Member member = new Member();

        System.out.println("Enter the name of the new member");
        member.setName(input.readLine());

        System.out.println("Please type their age");
        member.setAge(Integer.parseInt(input.readLine()));

        memberDao.insert(member);
        System.out.println(memberDao.listAll());
    }
}
