package com.payabbhi.net;

import static org.junit.Assert.*;

import com.payabbhi.exception.PayabbhiException;
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
      boolean result = Signature.verify(attributes, "secret_key");
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
      boolean result = Signature.verify(attributes, "secret_key");
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
      boolean result = Signature.verify(attributes, "secret_key");
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
      boolean result = Signature.verify(attributes, "secret_key");
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
      boolean result = Signature.verify(attributes, "secret_key");
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
      boolean result = Signature.verify(attributes, "Invalid_secret_key");
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
      boolean result = Signature.verify(attributes, null);
      fail("Error : testVerifySignatureForNullSecretKey didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }

  @Test
  public void testVerifySignatureForNullAttributes() {
    try {
      boolean result = Signature.verify(null, "secret_key");
      fail("Error : testVerifySignatureForNullAttributes didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }
}
