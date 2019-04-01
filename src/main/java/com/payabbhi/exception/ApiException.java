package com.payabbhi.exception;

/** An exception occurring when something unexpected takes place on Payabbhi's servers. */
public class ApiException extends PayabbhiException {

  private static final long serialVersionUID = 1L;

  public ApiException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  private int status;
  private String message;
  private String field;

  /**
   * Constructs a ApiException.
   *
   * @param status HTTP status code
   * @param message description of the exception
   * @param field field responsible for the exception
   */
  public ApiException(int status, String message, String field) {
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
