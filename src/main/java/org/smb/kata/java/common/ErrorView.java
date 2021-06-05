package org.smb.kata.java.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorView {

    private LocalDateTime timestamp = LocalDateTime.now();
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ErrorView(Integer status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
