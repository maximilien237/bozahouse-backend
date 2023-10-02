package net.bozahouse.backend.handlers;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.exception.ErrorCodes;
import net.bozahouse.backend.exception.InvalidEntityException;
import net.bozahouse.backend.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * @author maximilien kengne kongne
 * email : maximiliendenver@gmail.com
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exception, WebRequest webRequest) {
        log.warn(exception.getMessage(),exception.getCause());

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, notFound);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidOperationException exception, WebRequest webRequest) {
        log.warn(exception.getMessage(),exception.getCause());

        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidEntityException exception, WebRequest webRequest) {
        log.warn(exception.getMessage(),exception.getCause());

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();

        return new ResponseEntity<>(errorResponse, badRequest);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exception, WebRequest webRequest) {
        log.warn(exception.getMessage(),exception.getCause());

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCodes.BAD_CREDENTIALS)
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(Collections.singletonList("Login et / ou mot de passe incorrecte"))
                .build();

        return new ResponseEntity<>(errorResponse, badRequest);
    }

}
