package com.payabbhi.exception;

/**
 * An exception raised due to invalid API request for omitted required field or invalid field value.
 * etc
 */
public class InvalidRequestException extends PayabbhiException {

  private static final long serialVersionUID = 1L;

  public InvalidRequestException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public InvalidRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidRequestException(Throwable cause) {
    super(cause);
  }

  private int status;
  private String message;
  private String field;

  /**
   * Constructs a InvalidRequestException.
   *
   * @param status HTTP status code
   * @param message description of the exception
   * @param field field responsible for the exception
   */
  public InvalidRequestException(int status, String message, String field) {
    super(message);
    this.status = status;
    this.message = message;
    this.field = field;
  }

  @Override
  public String toString() {
    return String.format("%d:%s:%s", this.status, this.message, this.field);
  }
}
