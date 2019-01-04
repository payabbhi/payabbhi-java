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
public class OrderTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllOrders() throws PayabbhiException {
    PayabbhiCollection<Order> orders = Order.all();
    assertEquals(2, orders.count().intValue());
    assertNotEquals(null, orders.getData());
  }

  @Test
  public void testGetAllOrdersWithFilters() throws PayabbhiException {
    PayabbhiCollection<Order> orders =
        Order.all(
            new HashMap<String, Object>() {
              {
                put("authorized", true);
              }
            });
    assertEquals(563, orders.count().intValue());
    assertNotEquals(null, orders.getData());
    List<Order> orderlist = orders.getData();
    assertEquals(2, orderlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllOrdersWithInvalidFilters() throws PayabbhiException {

    Order.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveOrderDetails() throws PayabbhiException {
    Order order = Order.retrieve("order_aCsXtMDdTafnDbHd");
    assertEquals("order_aCsXtMDdTafnDbHd", order.get("id"));
    assertEquals("ordRefNo123456", order.get("merchant_order_id"));
  }

  @Test
  public void testCreateNewOrder() throws PayabbhiException {
    Order order =
        Order.create(
            new HashMap<String, Object>() {
              {
                put("merchant_order_id", "merchant_100");
                put("amount", 10000);
                put("currency", "INR");
              }
            });
    assertEquals("order_aCsXtMDdTafnDbHd", order.get("id"));
    assertEquals("merchant_100", order.get("merchant_order_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateOrderWithInvalidCurrency() throws PayabbhiException {
    Order.create(
        new HashMap<String, Object>() {
          {
            put("merchant_order_id", "merchant_101");
            put("amount", 10000);
            put("currency", "RUP"); // Invalid Currency
          }
        });
  }

  @Test
  public void testRetrievePaymentsOfOrder() throws PayabbhiException {
    PayabbhiCollection<Payment> payments = Order.payments("order_aCsXtMDdTafnDbHd");
    assertEquals(1, payments.count().intValue());
    assertNotEquals(null, payments.getData());
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrievePaymentsOfMissingOrder() throws PayabbhiException {
    Order.payments("hypothetical_order");
  }
}
