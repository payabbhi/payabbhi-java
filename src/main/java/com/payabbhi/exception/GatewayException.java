package com.payabbhi.exception;

/** Exception originating from the back-end PSP or Gateway. */
public class GatewayException extends PayabbhiException {

  public GatewayException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public GatewayException(String message, Throwable cause) {
    super(message, cause);
  }

  public GatewayException(Throwable cause) {
    super(cause);
  }

  private static final long serialVersionUID = 1L;

  private int status;
  private String message;
  private String field;

  /**
   * Constructs a GatewayException.
   *
   * @param status HTTP status code
   * @param message description of the exception
   * @param field field responsible for the exception
   */
  public GatewayException(int status, String message, String field) {
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
