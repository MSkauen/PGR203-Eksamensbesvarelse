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
    private TaskDao taskDao;
    private MembershipDao task_memberDao;


    public TaskManager() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("task-manager.properties"));

        dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.user"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();

        memberDao = new MemberDao(dataSource);
        taskDao = new TaskDao(dataSource);
        task_memberDao = new MembershipDao(dataSource);
    }

    public static void main(String[] args) throws IOException, SQLException {
        new TaskManager().run();
    }

    public void run() throws SQLException, IOException {
        System.out.println("Choose action: create [member] | create [task] | create [membership]");
        String action = input.readLine();

        switch (action.toLowerCase()){
            case "member":
                insertMember();
                break;
            case "task":
                insertTask();
                break;
            case "membership":
                insertMembership();
                break;
        }
    }

    private void insertTask() throws IOException, SQLException {
        System.out.println("Enter a task you would like to add");
        Task task = new Task();
        task.setName(input.readLine());
        taskDao.insert(task);

        System.out.println(taskDao.listAll());
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

    private void insertMembership() throws IOException, SQLException {
        Membership task_member = new Membership();

        System.out.println("Enter the task id");
        task_member.setTaskId(Long.parseLong(input.readLine()));

        System.out.println("Enter the member id");
        task_member.setMemberId(Long.parseLong(input.readLine()));

        task_memberDao.insert(task_member);
        System.out.println(task_memberDao.listAll());
    }
}
