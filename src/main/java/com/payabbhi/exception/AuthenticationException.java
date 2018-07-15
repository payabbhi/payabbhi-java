package com.payabbhi.exception;

/**
 * An exception raised due to Authentication error. This is usually encountered when the Access ID
 * or Secret Key is invalid. (Ensure that API keys in Portal are used in the code)
 */
public class AuthenticationException extends PayabbhiException {

  private static final long serialVersionUID = 1L;

  public AuthenticationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthenticationException(Throwable cause) {
    super(cause);
  }

  private int status;
  private String message;
  private String field;

  /**
   * Constructs a AuthenticationException
   *
   * @param status HTTP status code
   * @param message description of the exception
   * @param field field responsible for the exception
   */
  public AuthenticationException(int status, String message, String field) {
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
