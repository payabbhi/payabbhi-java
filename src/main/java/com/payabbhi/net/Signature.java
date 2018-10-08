package com.payabbhi.net;

import com.payabbhi.exception.PayabbhiException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class Signature {

  private static final String HMAC_SHA256 = "HmacSHA256";

  public static boolean verifyPaymentSignature(Map<String, String> attributes, String secretKey)
      throws PayabbhiException {
    if (attributes == null) {
      throw new PayabbhiException("Error : Attributes argument cannot be NULL.");
    }

    String expectedSignature = attributes.get("payment_signature");
    String orderId = attributes.get("order_id");
    String paymentId = attributes.get("payment_id");
    String payload = paymentId + '&' + orderId;
    return verifySignature(payload, expectedSignature, secretKey);
  }

  public static boolean verifyWebhookSignature(String data, String actualSignature, String secret, int replayInterval)
      throws PayabbhiException {
    if (data == "") {
      throw new PayabbhiException("Error : Data argument cannot be empty.");
    }
    if (actualSignature == "") {
      throw new PayabbhiException("Error : Signature argument cannot be empty.");
    }
    if (secret == "") {
      throw new PayabbhiException("Error : Secret argument cannot be empty.");
    }

    String[] entities = actualSignature.split(",");
    Map<String, String> payloadMap = new HashMap<>();
    for(String entity : entities) {
      String[] keyValue = entity.split("=");
      payloadMap.put(keyValue[0].trim(), keyValue[1]);
    }

    if (!payloadMap.containsKey("t") || !payloadMap.containsKey("v1") || (new Date().getTime())/1000 - Integer.parseInt(payloadMap.get("t")) > replayInterval) {
      return false;
    }
    String concatenatedString = data + '&' + payloadMap.get("t");
    return verifySignature(concatenatedString, payloadMap.get("v1"), secret);
  }


  private static boolean verifySignature(String payload, String signature, String secretKey)
      throws PayabbhiException {
    if (signature == null) {
      throw new PayabbhiException("Error : Payment Signature cannot be NULL.");
    }

    return MessageDigest.isEqual(sha256(payload, secretKey).getBytes(), signature.getBytes());
  }

  protected static String sha256(String payload, String secretKey) throws PayabbhiException {

    if (secretKey == null) {
      throw new PayabbhiException("Error : Secret Key cannot be NULL.");
    }

    try {
      Mac sha256 = Mac.getInstance(HMAC_SHA256);
      sha256.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), HMAC_SHA256));
      byte[] hash = sha256.doFinal(payload.getBytes());
      return new String(Hex.encodeHex(hash));
    } catch (Exception e) {
      throw new PayabbhiException(e.getMessage());
    }
  }
}
