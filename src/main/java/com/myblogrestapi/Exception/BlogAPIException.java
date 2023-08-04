package com.myblogrestapi.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BlogAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
//    public BlogAPIException(String message, HttpStatus status, String message1) {
//        super(message);
//        this.status = status;
//        this.message = message1;
//    }
}
