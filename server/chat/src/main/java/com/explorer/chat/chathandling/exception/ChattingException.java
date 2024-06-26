package com.explorer.chat.chathandling.exception;

import lombok.Getter;

@Getter
public class ChattingException extends RuntimeException {
    private final ChattingErrorCode errorCode;

    public ChattingException(ChattingErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
