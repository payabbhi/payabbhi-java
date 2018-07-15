package com.payabbhi.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.payabbhi.exception.*;
import org.junit.Test;

public class APIErrorResponseTest {

  @Test
  public void testInvalidRequestExceptionFromResponse() {
    APIErrorResponse errorResponse =
        new APIErrorResponse(400, "amount", "The amount is invalid", "invalid_request_error");

    PayabbhiException exception = errorResponse.toException();
    assertTrue(exception instanceof InvalidRequestException);
    assertEquals(exception.toString(), "400:The amount is invalid:amount");
  }

  @Test
  public void testAuthenticationExceptionFromResponse() {
    APIErrorResponse errorResponse =
        new APIErrorResponse(
            401, null, "Incorrect access_id or secret_key provided", "authentication_error");

    PayabbhiException exception = errorResponse.toException();
    assertTrue(exception instanceof AuthenticationException);
    assertEquals(exception.toString(), "401:Incorrect access_id or secret_key provided:null");
  }

  @Test
  public void testGatewayExceptionFromResponse() {
    APIErrorResponse errorResponse =
        new APIErrorResponse(502, null, "The gateway request timed out", "gateway_error");

    PayabbhiException exception = errorResponse.toException();
    assertTrue(exception instanceof GatewayException);
    assertEquals(exception.toString(), "502:The gateway request timed out:null");
  }

  @Test
  public void testApiExceptionFromResponse() {
    APIErrorResponse errorResponse =
        new APIErrorResponse(500, null, "Internal Server Error", "api_error");

    PayabbhiException exception = errorResponse.toException();
    assertTrue(exception instanceof ApiException);
    assertEquals(exception.toString(), "500:Internal Server Error:null");
  }

  @Test
  public void testExceptionFromUnseenResponse() {
    APIErrorResponse errorResponse =
        new APIErrorResponse(
            439, null, "\"XYZ resource\" is not supported", "unsupported_resource");
    PayabbhiException exception = errorResponse.toException();
    assertTrue(exception instanceof ApiException);
    assertEquals(exception.toString(), "439:\"XYZ resource\" is not supported:null");
  }
}
