package no.kristiania.taskManager.jdbc;

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
        return NOT_STARTED;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }
}
