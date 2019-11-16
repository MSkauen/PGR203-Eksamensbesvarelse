package no.kristiania.taskManager.jdbc;

import java.util.Objects;

public class Task {


    public enum TASK_STATUS {
        NOT_STARTED("Not started"),
        IN_PROGRESS("In progress"),
        FINISHED("Finished");

        String statusString;

        TASK_STATUS(String statusString){
            this.statusString = statusString;
        }
    }

    private String name;
    private String statusString;
    private long id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTaskStatus(TASK_STATUS taskStatus) {
        this.statusString = taskStatus.statusString;
    }

    public String getTaskStatus() {
        return statusString;
    }
}
