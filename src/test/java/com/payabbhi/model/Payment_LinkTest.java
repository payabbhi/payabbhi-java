package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class Payment_LinkTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllPayment_Links() throws PayabbhiException {
    PayabbhiCollection<Payment_Link> payment_links = Payment_Link.all();
    assertEquals(2, payment_links.count().intValue());
    assertNotEquals(null, payment_links.getData());
  }

  @Test
  public void testGetAllPayment_LinksWithFilters() throws PayabbhiException {
    PayabbhiCollection<Payment_Link> payment_links =
        Payment_Link.all(
            new HashMap<String, Object>() {
              {
                put("count", 1);
              }
            });
    assertEquals(2, payment_links.count().intValue());
    assertNotEquals(null, payment_links.getData());
    List<Payment_Link> payment_linklist = payment_links.getData();
    assertEquals(1, payment_linklist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllPayment_LinksWithInvalidFilters() throws PayabbhiException {

    Payment_Link.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrievePayment_LinkDetails() throws PayabbhiException {
    Payment_Link payment_link = Payment_Link.retrieve("invc_NRFJkTGyZYo03cPD");
    assertEquals("invc_NRFJkTGyZYo03cPD", payment_link.get("id"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", payment_link.get("customer_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrievePayment_LinkWithInvalidID() throws PayabbhiException {
    Payment_Link.retrieve("invc_invalid");
  }

  @Test
  public void testCreateNewPayment_Link() throws PayabbhiException {
    Map<String, Object> options = new HashMap<>();
    options.put("amount", 100);
    options.put("currency", "INR");
    options.put("customer_id", "cust_2WmsQoSRZMWWkcZg");
    Payment_Link payment_link = Payment_Link.create(options);
    assertEquals("invc_NRFJkTGyZYo03cPD", payment_link.get("id"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", payment_link.get("customer_id"));
    assertEquals((Integer) 100, payment_link.get("amount"));
    assertEquals("INR", payment_link.get("currency"));
    assertEquals("Test_R123", payment_link.get("receipt_no"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNoPayment_LinkWhenParamsLessThanMin() throws PayabbhiException {
    Map<String, Object> options = new HashMap<>();
    options.put("customer_id", "cust_J5fF1cj1KfSuI63S");
    Payment_Link.create(options);
  }

  @Test
  public void testCancelPayment_Link() throws PayabbhiException {
    Payment_Link payment_link = Payment_Link.cancel("invc_NRFJkTGyZYo03cPD");
    assertEquals("cust_2WmsQoSRZMWWkcZg", payment_link.get("customer_id"));
    assertEquals("Test_R123", payment_link.get("receipt_no"));
    assertEquals("cancelled", payment_link.get("status"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCancelPayment_LinkWithInvalidID() throws PayabbhiException {
    Payment_Link.cancel("invc_invalid");
  }

  @Test
  public void testRetrievePaymentsOfPayment_Link() throws PayabbhiException {
    PayabbhiCollection<Payment> payments = Payment_Link.payments("invc_NRFJkTGyZYo03cPD");
    assertEquals(1, payments.count().intValue());
    assertNotEquals(null, payments.getData());
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrievePaymentsOfPayment_LinkWithInvalidPaymentLinkID() throws PayabbhiException {
    Payment_Link.payments("invc_invalid");
  }
  
  
}
