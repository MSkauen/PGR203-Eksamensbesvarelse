package no.kristiania.taskManager.http;

public enum STATUS_CODE {
    OK(200, "OK"),
    FOUND(302, "FOUND"),
    BAD_REQUEST(400, "BAD REQUEST"),
    UNAUTHORISED(401, "UNAUTHORISED"),
    NOT_FOUND(404, "NOT FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

    public int code;
    public String status;

    STATUS_CODE(int code, String status){
        this.code = code;
        this.status = status;
    }

    public static STATUS_CODE getCode(int code){
        for(STATUS_CODE e : STATUS_CODE.values()){
            if(e.code == code){
                return e;
            }
        }
        return STATUS_CODE.INTERNAL_SERVER_ERROR;
    }
}
