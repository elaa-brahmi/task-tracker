package com.example.task_tracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {
    NO_CODE(0,NOT_IMPLEMENTED,"no code"),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST,"incorrect current password"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,BAD_REQUEST,"the new password does not match"),
    Task_Not_Found(401,NOT_FOUND,"task not found"),
    Email_USED(500,INTERNAL_SERVER_ERROR,"email used try another one"),
    ACCOUNT_LOCKED(302,FORBIDDEN,"User account is locked"),
    ACCOUNT_DISABLED(303,FORBIDDEN,"User account is disabled"),
    BAD_CREDENTIALS(304,FORBIDDEN,"login and / or password is incorrect"),

    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
