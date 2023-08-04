package com.myblogrestapi.Exception;

import com.myblogrestapi.Entity.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails>handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                       WebRequest webRequest){
        ErrorDetails error=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails>handleBlogAPIException(BlogAPIException exception,
                                                                       WebRequest webRequest){
        ErrorDetails error=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails>handleGlobalException(Exception exception,
                                                              WebRequest webRequest) {
        ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
