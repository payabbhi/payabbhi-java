package com.payabbhi.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.payabbhi.exception.PayabbhiException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class SignatureTest {

  private static String paymentsignature =
      "e70360e32919311d31cbc9b558ea9024715b816ce64293ffc992459a94daac42";

  @Test
  public void testVerifySignatureForValidPayment() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", "dummy_payment_id");
    attributes.put("payment_signature", paymentsignature);
    try {
      boolean result = Signature.verifyPaymentSignature(attributes, "secret_key");
      assertEquals(true, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForValidPayment - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForInvalidPaymentSignature() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", "dummy_payment_id");
    attributes.put("payment_signature", paymentsignature + "random");
    try {
      boolean result = Signature.verifyPaymentSignature(attributes, "secret_key");
      assertEquals(false, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForInvalidPaymentSignature - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForNullPaymentSignature() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", "dummy_payment_id");
    attributes.put("payment_signature", null);
    try {
      boolean result = Signature.verifyPaymentSignature(attributes, "secret_key");
      fail("Error : testVerifySignatureForNullPaymentSignature didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }

  @Test
  public void testVerifySignatureForNullOrderID() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", null);
    attributes.put("payment_id", "dummy_payment_id");
    attributes.put("payment_signature", paymentsignature);
    try {
      boolean result = Signature.verifyPaymentSignature(attributes, "secret_key");
      assertEquals(false, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForNullOrderID - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForNullPaymentID() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", null);
    attributes.put("payment_signature", paymentsignature);
    try {
      boolean result = Signature.verifyPaymentSignature(attributes, "secret_key");
      assertEquals(false, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForNullPaymentID - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForIncorrectSecretKey() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", "dummy_payment_id");
    attributes.put("payment_signature", paymentsignature);
    try {
      boolean result = Signature.verifyPaymentSignature(attributes, "Invalid_secret_key");
      assertEquals(false, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForIncorrectSecretKey - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForNullSecretKey() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", null);
    attributes.put("payment_signature", paymentsignature);
    try {
      boolean result = Signature.verifyPaymentSignature(attributes, null);
      fail("Error : testVerifySignatureForNullSecretKey didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }

  @Test
  public void testVerifySignatureForNullAttributes() {
    try {
      boolean result = Signature.verifyPaymentSignature(null, "secret_key");
      fail("Error : testVerifySignatureForNullAttributes didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }

  @Test
  public void testVerifySignatureForValidWebhookWithoutReplay() {
    try {
      String payload = "test_payload";
      Integer t = (int) ((new Date().getTime()) / 1000);
      String secret = "skw_live_jHNxKsDqJusco5hA";
      String message = payload + '&' + t.toString();
      String v1 = Signature.sha256(message, secret);
      String actualSignature = "t=" + t.toString() + ", v1=" + v1;
      boolean result = Signature.verifyWebhookSignature(payload, actualSignature, secret, 300);
      assertEquals(true, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForValidWebhookWithoutReplay - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForValidWebhookWithReplay() {
    try {
      String payload = "test_payload";
      Integer t = (int) ((new Date().getTime()) / 1000);
      String secret = "skw_live_jHNxKsDqJusco5hA";
      String message = payload + '&' + t.toString();
      String v1 = Signature.sha256(message, secret);
      String actualSignature = "t=" + t.toString() + ", v1=" + v1;
      boolean result = Signature.verifyWebhookSignature(payload, actualSignature, secret, 40);
      assertEquals(true, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForValidWebhookWithReplay - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForValidWebhookWithReplayAttack() {
    try {
      String payload = "test_payload";
      Integer t = (int) ((new Date().getTime()) / 1000 - 20);
      String secret = "skw_live_jHNxKsDqJusco5hA";
      String message = payload + '&' + t.toString();
      String v1 = Signature.sha256(message, secret);
      String actualSignature = "t=" + t.toString() + ", v1=" + v1;
      boolean result = Signature.verifyWebhookSignature(payload, actualSignature, secret, 10);
      assertEquals(false, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForValidWebhookWithReplayAttack - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForInValidWebhook() {
    try {
      String payload = "test_payload";
      Integer t = (int) ((new Date().getTime()) / 1000 - 20);
      String secret = "skw_live_jHNxKsDqJusco5hA";
      String message = payload + '&' + t.toString();
      String v1 = Signature.sha256(message, secret + "random");
      String actualSignature = "t=" + t.toString() + ", v1=" + v1;
      boolean result = Signature.verifyWebhookSignature(payload, actualSignature, secret, 10);
      assertEquals(false, result);
    } catch (PayabbhiException e) {
      fail("Error : testVerifySignatureForInValidWebhook - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForEmptyData() {
    try {
      boolean result = Signature.verifyWebhookSignature("", "actualSignature", "secret", 10);
      assertEquals(false, result);
      fail("Error : testVerifySignatureForNullData didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }

  @Test
  public void testVerifySignatureForEmptyActualSignature() {
    try {
      boolean result = Signature.verifyWebhookSignature("data", "", "secret", 10);
      assertEquals(false, result);
      fail("Error : testVerifySignatureForEmptyActualSignature didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }

  @Test
  public void testVerifySignatureForEmptySecret() {
    try {
      boolean result = Signature.verifyWebhookSignature("data", "actualSignature", "", 10);
      assertEquals(false, result);
      fail("Error : testVerifySignatureForEmptySecret didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }
}
