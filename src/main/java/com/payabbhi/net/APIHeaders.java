package com.payabbhi.net;

import com.payabbhi.BuildInfo;
import com.payabbhi.Payabbhi;
import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import org.json.JSONObject;

public class APIHeaders {

  /** Return API headers. */
  public static Headers get() {

    Builder b = new Headers.Builder();
    b.set("Authorization", Credentials.basic(Payabbhi.accessId, Payabbhi.secretKey))
        .set("Content-Type", "application/json")
        .set("X-Payabbhi-Client-User-Agent", clientUserAgent())
        .set("User-Agent", userAgent());
    return b.build();
  }

  private static String userAgent() {
    return String.format("Payabbhi/v1 JavaBindings/%s %s", longVersion(), Payabbhi.appInfo());
  }

  private static String longVersion() {
    return String.format("%s-%s", BuildInfo.VERSION, BuildInfo.BUILD_HASH);
  }

  private static String clientUserAgent() {
    JSONObject values = new JSONObject();
    values.put("binding_version", longVersion());
    values.put("lang", "java");
    values.put("lang_version", System.getProperty("java.version"));
    values.put("publisher", "payabbhi");
    values.put("uname", System.getProperty("user.name"));
    values.put("application", Payabbhi.appInfo());
    return values.toString();
  }
}
