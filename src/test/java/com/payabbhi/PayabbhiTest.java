package com.payabbhi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.payabbhi.exception.PayabbhiException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class PayabbhiTest {

  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "access_id";
    Payabbhi.secretKey = "secret_key";
  }

  @Test
  public void testverifyPaymentSignature() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", "dummy_payment_id");
    attributes.put(
        "payment_signature", "e70360e32919311d31cbc9b558ea9024715b816ce64293ffc992459a94daac42");
    try {
      boolean result = Payabbhi.verifyPaymentSignature(attributes);
      assertEquals(true, result);
    } catch (PayabbhiException e) {
      fail("Error : Unexpected Exception - " + e.toString());
    }
  }

  @Test
  public void testVerifySignatureForNullPaymentSignature() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("order_id", "dummy_order_id");
    attributes.put("payment_id", "dummy_payment_id");
    attributes.put("payment_signature", null);
    try {
      boolean result = Payabbhi.verifyPaymentSignature(attributes);
      fail("Error : Signature Verification didn't throw PayabbhiException");
    } catch (PayabbhiException e) {
      assertTrue(e instanceof PayabbhiException);
    }
  }
}
