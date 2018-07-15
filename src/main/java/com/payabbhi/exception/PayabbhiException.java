package com.payabbhi.exception;

public class PayabbhiException extends Exception {

  private static final long serialVersionUID = 1L;

  public PayabbhiException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public PayabbhiException(String message, Throwable cause) {
    super(message, cause);
  }

  public PayabbhiException(Throwable cause) {
    super(cause);
  }

  public PayabbhiException(String message) {
    super(message);
  }
}
