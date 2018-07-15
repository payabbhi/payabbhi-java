package com.payabbhi.net;

import com.payabbhi.exception.PayabbhiException;
import java.security.MessageDigest;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class Signature {

  private static final String HMAC_SHA256 = "HmacSHA256";

  public static boolean verify(Map<String, String> attributes, String secretKey)
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

  private static boolean verifySignature(String payload, String signature, String secretKey)
      throws PayabbhiException {
    if (signature == null) {
      throw new PayabbhiException("Error : Payment Signature cannot be NULL.");
    }

    return MessageDigest.isEqual(sha256(payload, secretKey).getBytes(), signature.getBytes());
  }

  private static String sha256(String payload, String secretKey) throws PayabbhiException {

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
