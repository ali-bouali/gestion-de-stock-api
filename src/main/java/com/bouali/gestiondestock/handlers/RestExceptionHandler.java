package com.bouali.gestiondestock.handlers;

import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception, WebRequest webRequest) {

    final HttpStatus notFound = HttpStatus.NOT_FOUND;
    final ErrorDto errorDto = ErrorDto.builder()
        .code(exception.getErrorCode())
        .httpCode(notFound.value())
        .message(exception.getMessage())
        .build();

    return new ResponseEntity<>(errorDto, notFound);
  }

  @ExceptionHandler(InvalidOperationException.class)
  public ResponseEntity<ErrorDto> handleException(InvalidOperationException exception, WebRequest webRequest) {

    final HttpStatus notFound = HttpStatus.BAD_REQUEST;
    final ErrorDto errorDto = ErrorDto.builder()
        .code(exception.getErrorCode())
        .httpCode(notFound.value())
        .message(exception.getMessage())
        .build();

    return new ResponseEntity<>(errorDto, notFound);
  }

  @ExceptionHandler(InvalidEntityException.class)
  public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception, WebRequest webRequest) {
    final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    final ErrorDto errorDto = ErrorDto.builder()
        .code(exception.getErrorCode())
        .httpCode(badRequest.value())
        .message(exception.getMessage())
        .errors(exception.getErrors())
        .build();

    return new ResponseEntity<>(errorDto, badRequest);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception, WebRequest webRequest) {
    final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    final ErrorDto errorDto = ErrorDto.builder()
        .code(ErrorCodes.BAD_CREDENTIALS)
        .httpCode(badRequest.value())
        .message(exception.getMessage())
        .errors(Collections.singletonList("Login et / ou mot de passe incorrecte"))
        .build();

    return new ResponseEntity<>(errorDto, badRequest);
  }

}
