package no.kristiania.taskManager.jdbc;

import java.util.Objects;

public class Task {


    public enum TASK_STATUS {
        NOT_STARTED("Not started"),
        IN_PROGRESS("In progress"),
        FINISHED("Finished");

        private String statusString;

        TASK_STATUS(String statusString) {
            this.setStatusString(statusString);
        }

        public static TASK_STATUS getTaskStatus(String inputString) {
            for (TASK_STATUS e : TASK_STATUS.values()) {
                if (e.getStatusString().equals(inputString)) {
                    return e;
                }
            }
            return null;
        }

        public String getStatusString() {
            return statusString;
        }

        public void setStatusString(String statusString) {
            this.statusString = statusString;
        }
    }

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
