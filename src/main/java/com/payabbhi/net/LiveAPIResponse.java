package com.payabbhi.net;

public class LiveAPIResponse implements APIResponse {
  String body;
  int status;

  public LiveAPIResponse(String body, int status) {
    super();
    this.body = body;
    this.status = status;
  }

  @Override
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
