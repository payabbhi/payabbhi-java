package com.payabbhi.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.model.BaseTest;
import com.payabbhi.model.Order;
import com.payabbhi.model.PayabbhiCollection;
import com.payabbhi.model.Payment;
import com.payabbhi.model.Refund;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class APIResourceTest extends BaseTest {

  private String ordersSrc;
  private String paymentsSrc;
  private String paymentOfOrderSrc;
  private String refundsSrc;
  private String refundOfPaymentSrc;
  private String orderSrc;

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws IOException {
    ordersSrc = getResourceAsString("/collections/orders.json");
    paymentsSrc = getResourceAsString("/collections/payments.json");
    paymentOfOrderSrc = getResourceAsString("/collections/paymentoforder.json");
    refundsSrc = getResourceAsString("/collections/refunds.json");
    refundOfPaymentSrc = getResourceAsString("/collections/refundofpayment.json");

    orderSrc = getResourceAsString("/collections/order.json");
  }

  @Test
  public void testUrlForOrderClass() {
    String url = APIResource.urlFor(Order.class);
    assertEquals("https://payabbhi.com/api/v1/orders", url);
  }

  @Test
  public void testUrlForPaymentClass() {
    String url = APIResource.urlFor(Payment.class);
    assertEquals("https://payabbhi.com/api/v1/payments", url);
  }

  @Test
  public void testUrlForRefundClass() {
    String url = APIResource.urlFor(Refund.class);
    assertEquals("https://payabbhi.com/api/v1/refunds", url);
  }

  @Test
  public void testUrlForSingleOrder() {
    String url = APIResource.urlFor(Order.class, "order_id");
    assertEquals("https://payabbhi.com/api/v1/orders/order_id", url);
  }

  @Test
  public void testUrlForSinglePayment() {
    String url = APIResource.urlFor(Payment.class, "payment_id");
    assertEquals("https://payabbhi.com/api/v1/payments/payment_id", url);
  }

  @Test
  public void testUrlForSingleRefund() {
    String url = APIResource.urlFor(Refund.class, "refund_id");
    assertEquals("https://payabbhi.com/api/v1/refunds/refund_id", url);
  }

  @Test
  public void testUrlForPaymentCapture() {
    String url = APIResource.urlFor(Payment.class, "pay_mUy6UYsRvIxH9Qi2", "capture");
    assertEquals("https://payabbhi.com/api/v1/payments/pay_mUy6UYsRvIxH9Qi2/capture", url);
  }

  @Test
  public void testUrlForGetRefundsOfPayment() {
    String url = APIResource.urlFor(Payment.class, "payment_id", Refund.class);
    assertEquals("https://payabbhi.com/api/v1/payments/payment_id/refunds", url);
  }

  // This may not be relevant for your product.
  @Test
  public void testUrlForClassNestedInstance() {
    String url =
        APIResource.urlFor(
            Order.class, "order_aCsXtMDdTafnDbHd", Payment.class, "pay_mUy6UYsRvIxH9Qi2");
    assertEquals(
        "https://payabbhi.com/api/v1/orders/order_aCsXtMDdTafnDbHd/payments/pay_mUy6UYsRvIxH9Qi2",
        url);
  }

  @Test
  public void testAddQueryParametersNullCount() {
    Map<String, Object> params = new HashMap<>();
    params.put("count", null);
    params.put("skip", 10);
    String url = APIResource.withParams("https://payabbhi.com/api/v1/orders", params);
    assertEquals("https://payabbhi.com/api/v1/orders?count=&skip=10", url);
  }

  @Test
  public void testAddQueryParametersWhiteSpaceCount() {
    Map<String, Object> params = new HashMap<>();
    params.put("count", "\n ");
    params.put("skip", 10);
    String url = APIResource.withParams("https://payabbhi.com/api/v1/orders", params);
    assertEquals("https://payabbhi.com/api/v1/orders?count=%0A+&skip=10", url);
  }

  @Test
  public void testAddQueryParametersValidAttributeValue() {
    Map<String, Object> params = new HashMap<>();
    params.put("count", "10");
    params.put("attribute", true);
    String url = APIResource.withParams("https://payabbhi.com/api/v1/orders", params);
    assertEquals("https://payabbhi.com/api/v1/orders?count=10&attribute=true", url);
  }

  @Test
  public void testAddQueryParametersInvalidAttributeValue() {
    Map<String, Object> params = new HashMap<>();
    params.put("count", "10");
    params.put("attribute", "true\t");
    String url = APIResource.withParams("https://payabbhi.com/api/v1/orders", params);
    assertEquals("https://payabbhi.com/api/v1/orders?count=10&attribute=true%09", url);
  }

  @Test
  public void testUnmarshalCollectionOrders() throws PayabbhiException {
    PayabbhiCollection<Order> orders = APIResource.unmarshalCollection(ordersSrc, Order.class);
    assertEquals(564, orders.count().intValue());
    assertEquals(10, orders.getData().size());
  }

  @Test
  public void testUnmarshalCollectionPayments() throws PayabbhiException {
    PayabbhiCollection<Payment> payments =
        APIResource.unmarshalCollection(paymentsSrc, Payment.class);
    assertEquals(171, payments.count().intValue());
    assertEquals(10, payments.getData().size());
  }

  @Test
  public void testUnmarshalCollectionPaymentsOfOrder() throws PayabbhiException {
    PayabbhiCollection<Payment> payments =
        APIResource.unmarshalCollection(paymentOfOrderSrc, Payment.class);
    assertEquals(2, payments.count().intValue());
    assertEquals(2, payments.getData().size());
  }

  @Test
  public void testUnmarshalCollectionRefunds() throws PayabbhiException {
    PayabbhiCollection<Refund> refunds = APIResource.unmarshalCollection(refundsSrc, Refund.class);

    assertEquals(61, refunds.count().intValue());
    assertEquals(10, refunds.getData().size());
  }

  @Test
  public void testUnmarshalCollectionRefundsOfPayment() throws PayabbhiException {

    PayabbhiCollection<Refund> refunds =
        APIResource.unmarshalCollection(refundOfPaymentSrc, Refund.class);

    assertEquals(2, refunds.count().intValue());
    assertEquals(2, refunds.getData().size());
  }

  @Test(expected = PayabbhiException.class)
  public void testInvalidOrdersJSON() throws PayabbhiException {
    APIResource.unmarshalCollection("{}", Order.class);
  }

  @Test
  public void testUnmarshallObject() throws PayabbhiException {
    Order order = APIResource.unmarshalObject(orderSrc, Order.class);
    assertNotNull(order);
    assertEquals("order", order.get("object").toString());
  }

  @Test(expected = PayabbhiException.class)
  public void testInvalidOrderJSON() throws PayabbhiException {
    APIResource.unmarshalObject("", Order.class);
  }
}
