package net.bozahouse.backend.exception;

import lombok.Getter;

import java.util.List;

public class EmptyListException extends RuntimeException {

  @Getter
  private ErrorCodes errorCode;
  @Getter
  private List<String> errors;

  public EmptyListException(String message) {
    super(message);
  }

  public EmptyListException(String message, Throwable cause) {
    super(message, cause);
  }

  public EmptyListException(String message, Throwable cause, ErrorCodes errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public EmptyListException(String message, ErrorCodes errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public EmptyListException(String message, ErrorCodes errorCode, List<String> errors) {
    super(message);
    this.errorCode = errorCode;
    this.errors = errors;
  }

}
