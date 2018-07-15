package com.payabbhi.net;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.ApiException;
import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource.Method;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LiveFetcher implements Fetcher {
  @Override
  public APIResponse fetch(Method method, String url, String body) throws PayabbhiException {

    Request request = request(method, url, APIHeaders.get(), body);

    OkHttpClient client = client();

    Response response;
    try {
      response = client.newCall(request).execute();
      return new LiveAPIResponse(response.body().string(), response.code());
    } catch (IOException e) {
      throw new ApiException(e.getMessage(), e);
    }
  }

  private OkHttpClient client() {
    OkHttpClient client =
        new Builder()
            .writeTimeout(Payabbhi.writeTimeout(), TimeUnit.SECONDS)
            .readTimeout(Payabbhi.readTimeout(), TimeUnit.SECONDS)
            .connectTimeout(Payabbhi.connectTimeout(), TimeUnit.SECONDS)
            .build();
    return client;
  }

  private Request request(Method method, String url, Headers headers, String body) {
    Request request =
        new Request.Builder()
            .url(url)
            .headers(headers)
            .method(method.toString(), body(method, body))
            .build();
    return request;
  }

  private RequestBody body(Method method, String body) {
    if (method == Method.GET) {
      return null;
    } else {
      return RequestBody.create(null, body);
    }
  }
}
