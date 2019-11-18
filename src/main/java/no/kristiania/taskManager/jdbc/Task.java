package no.kristiania.taskManager.jdbc;

import java.util.Objects;

public class Task {


    private String name;
    private String status;
    private long id;
    private long projectId;

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

    public void setTaskStatus(String taskStatus) {
        this.status = taskStatus;
    }

    public String getTaskStatus() {
        return status;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long project_id) {
        this.projectId = project_id;
    }


}
