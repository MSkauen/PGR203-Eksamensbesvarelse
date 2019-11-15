package no.kristiania.taskManager.jdbc;

import java.util.Objects;

public class Membership {

    private long id;
    private long taskId;
    private long memberId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membership task_member = (Membership) o;
        return Objects.equals(memberId, task_member.memberId);
    }

    @Override
    public String toString() {
        return "TaskMember{" +
                "id=" + id +
                ", taskId=" + taskId +
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

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getTaskId() {
        return taskId;
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
