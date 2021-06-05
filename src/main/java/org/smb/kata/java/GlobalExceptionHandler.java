package org.smb.kata.java;

import org.smb.kata.java.common.ErrorView;
import org.smb.kata.java.electricity.execption.LoadCsvFileException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(LoadCsvFileException.class)
    public ErrorView handleLoadCsvFileException(LoadCsvFileException e, HttpServletRequest r) {
        return new ErrorView(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                e.getMessage());
    }
}
