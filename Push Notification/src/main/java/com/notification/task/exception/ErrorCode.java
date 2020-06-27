package com.notification.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode {

    APPLICATION_ERROR("001", "Application error"),
    AUTHENTICATION_ERROR("002", "Invalid User");

    @Getter
    private String code;

    @Getter
    private String description;
}
