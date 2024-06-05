//package com.nr.backendapi.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<Object> handleStudentNotFoundException(RuntimeException exception) {
//        log.error("Runtime Exception - {}", exception.getLocalizedMessage());
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(exception.getMessage());
//    }
//}