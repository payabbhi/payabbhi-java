package com.payabbhi.net;

import com.payabbhi.exception.ApiException;
import com.payabbhi.exception.AuthenticationException;
import com.payabbhi.exception.GatewayException;
import com.payabbhi.exception.InvalidRequestException;
import com.payabbhi.exception.PayabbhiException;

public class APIErrorResponse {
  private static final String API_ERROR = "api_error";
  private static final String GATEWAY_ERROR = "gateway_error";
  private static final String AUTHENTICATION_ERROR = "authentication_error";
  private static final String INVALID_REQUEST_ERROR = "invalid_request_error";

  /** Return the API error response constructed from status,field,message and type. */
  public APIErrorResponse(int status, String field, String message, String type) {
    super();
    this.status = status;
    this.field = field;
    this.message = message;
    this.type = type;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  private int status;
  private String field;
  private String message;
  private String type;

  /** Return the Exception corresponding to API error message. */
  public PayabbhiException toException() {
    switch (this.type) {
      case INVALID_REQUEST_ERROR:
        return new InvalidRequestException(this.status, this.message, this.field);
      case AUTHENTICATION_ERROR:
        return new AuthenticationException(this.status, this.message, this.field);
      case GATEWAY_ERROR:
        return new GatewayException(this.status, this.message, this.field);
      case API_ERROR:
        return new ApiException(this.status, this.message, this.field);
      default:
        return new ApiException(this.status, this.message, this.field);
    }
  }
}
