package no.kristiania.taskManager.jdbc;

import java.util.Objects;

public class TaskMember {

    private long id;
    private long projectId;
    private long memberId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskMember task_member = (TaskMember) o;
        return Objects.equals(memberId, task_member.memberId);
    }

    @Override
    public String toString() {
        return "TaskMember{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", memberId=" + memberId +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }


    public void setMemberId(long memberId){
        this.memberId = memberId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
