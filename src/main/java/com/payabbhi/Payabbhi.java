package com.payabbhi;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.Signature;
import java.util.HashMap;
import java.util.Map;

public class Payabbhi {
  private static final String AI_EMPTY = "";
  private static final String AI_URL = "url";
  private static final String AI_VERSION = "version";
  private static final String AI_NAME = "name";
  private static final String DEFAULT_APIBASE = "https://payabbhi.com/api/v1";

  public static volatile String accessId;
  public static volatile String secretKey;
  private static volatile String apiBase = DEFAULT_APIBASE;
  private static volatile Map<String, String> appInfo = null;

  private static volatile int connectTimeout = 10;
  private static volatile int readTimeout = 10;
  private static volatile int writeTimeout = 10;

  /**
   * Verifies payment signature
   *
   * @param attributes a map of the following attributes and their values [order_id, payment_id,
   *     payment_signature]
   * @return true if the payment signature is verified successfully
   * @throws PayabbhiException in case of any problem while recomputing the signature
   */
  public static boolean verifyPaymentSignature(Map<String, String> attributes)
      throws PayabbhiException {
    return Signature.verifyPaymentSignature(attributes, secretKey);
  }


  /**
   * Verifies webhook signature
   *
   * @param payload containing payload data
   * @param signature containing the actual signature
   * @param secret containing the secret
   * @return true if the webhook signature is verified successfully
   * @throws PayabbhiException in case of any problem while recomputing the signature
   */
  public static boolean verifyWebhookSignature(String payload, String signature, String secret)
      throws PayabbhiException {
    return Payabbhi.verifyWebhookSignature(payload, signature, secret, 300);
  }

  /**
   * Verifies webhook signature
   *
   * @param payload containing payload data
   * @param signature containing the actual signature
   * @param secret containing the secret
   * @param replayInterval containing the replay interval in seconds
   * @return true if the webhook signature is verified successfully
   * @throws PayabbhiException in case of any problem while recomputing the signature
   */
  public static boolean verifyWebhookSignature(String payload, String signature, String secret, int replayInterval)
      throws PayabbhiException {
    return Signature.verifyWebhookSignature(payload, signature, secret, replayInterval);
  }



  public static String apiBase() {
    return apiBase;
  }

  public static void setApiBase(String apiBase) {
    Payabbhi.apiBase = apiBase;
  }

  /**
   * Sets additional information about your app.
   *
   * @param name name of the application
   * @param version version of the application
   * @param url url of the application
   */
  public static void setAppInfo(String name, String version, String url) {
    if (appInfo == null) {
      appInfo = new HashMap<String, String>();
    }

    appInfo.put(AI_NAME, name);
    appInfo.put(AI_VERSION, version);
    appInfo.put(AI_URL, url);
  }

  /**
   * Returns a formatted string representing the information about your app.
   *
   * @return information about your app
   */
  public static String appInfo() {
    if (appInfo == null) {
      return AI_EMPTY;
    }
    return String.format(
        "%s/%s (%s)", appInfo.get(AI_NAME), appInfo.get(AI_VERSION), appInfo.get(AI_URL));
  }

  /**
   * Returns the connection timeout value.
   *
   * @return timeout value in seconds
   */
  public static int connectTimeout() {
    return connectTimeout;
  }

  /**
   * Sets the connection timeout for new connections
   *
   * @param connectTimeout timeout value in seconds
   */
  public static void setConnectTimeout(int connectTimeout) {
    Payabbhi.connectTimeout = connectTimeout;
  }

  /**
   * Returns the read timeout value.
   *
   * @return timeout value in seconds
   */
  public static int readTimeout() {
    return readTimeout;
  }

  /**
   * Sets the read timeout for new connections
   *
   * @param readTimeout timeout value in seconds
   */
  public static void setReadTimeout(int readTimeout) {
    Payabbhi.readTimeout = readTimeout;
  }

  /**
   * Returns the write timeout value.
   *
   * @return timeout value in seconds
   */
  public static int writeTimeout() {
    return writeTimeout;
  }

  /**
   * Sets the write timeout for new connections
   *
   * @param writeTimeout timeout value in seconds
   */
  public static void setWriteTimeout(int writeTimeout) {
    Payabbhi.writeTimeout = writeTimeout;
  }
}
