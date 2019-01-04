package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class RefundTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllRefunds() throws PayabbhiException {
    PayabbhiCollection<Refund> refunds = Refund.all();
    assertEquals(60, refunds.count().intValue());
    assertNotEquals(null, refunds.getData());
    List<Refund> reflist = refunds.getData();
    assertEquals(10, reflist.size());
  }

  @Test
  public void testGetAllRefundsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Refund> refunds =
        Refund.all(
            new HashMap<String, Object>() {
              {
                put("count", 2);
              }
            });
    assertEquals(60, refunds.count().intValue());
    assertNotEquals(null, refunds.getData());
    List<Refund> reflist = refunds.getData();
    assertEquals(2, reflist.size());
  }

  @Test
  public void testRetrieveRefundDetails() throws PayabbhiException {
    Refund refinfo = Refund.retrieve("rfnd_nMegjyzBnXfUZTb3");
    assertEquals("rfnd_nMegjyzBnXfUZTb3", refinfo.get("id"));
    assertEquals("pay_1x541IRDjvVULyqP", refinfo.get("payment_id"));
  }

  @Test
  public void testCreateRefundForPayment() throws PayabbhiException {
    Refund refund =
        Refund.create(
            "pay_C5OcIOxiCrZXAcHG",
            new HashMap<String, Object>() {
              {
                put("amount", 100);
              }
            });

    assertEquals("rfnd_", refund.get("id").toString().substring(0, 5));
    assertEquals(Integer.valueOf(100), refund.get("amount"));
    assertEquals("pay_C5OcIOxiCrZXAcHG", refund.get("payment_id"));
  }

  @Test
  public void testCreateCompleteRefundForPayment() throws PayabbhiException {
    Refund refund = Refund.create("pay_reSjReWuDnOVz2zr");
    assertEquals("rfnd_", refund.get("id").toString().substring(0, 5));
    assertEquals("pay_reSjReWuDnOVz2zr", refund.get("payment_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateInvalidRefundForPayment() throws PayabbhiException {
    Refund.create(
        "pay_C5OcIOxiCrZXAcHG",
        new HashMap<String, Object>() {
          {
            put("amount", 10000);
          }
        });
  }
}
