package _2._millionaire.task.exception;

import _2._millionaire.BaseErrorCode;

public enum TaskErrorCode implements BaseErrorCode {
    TASK_NOT_FOUND("존재하지 않는 TASK입니다.", 404),
    NOT_ADMIN("Admin이 아닙니다.", 403),
    MEMBER_NOT_FOUND("존재하지 않는 사용자입니다.", 404),
    GROUP_NOT_FOUND("존재하지 않는 그룹입니다.", 404),
    MEMBER_NOT_IN_GROUP("해당 맴버가 그룹에 존재하지 않습니다.", 404);

    private final String message;
    private final int statusCode;

    TaskErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
