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
public class SubscriptionTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllSubscriptions() throws PayabbhiException {
    PayabbhiCollection<Subscription> subscriptions = Subscription.all();
    assertEquals(2, subscriptions.count().intValue());
    assertNotEquals(null, subscriptions.getData());
  }

  @Test
  public void testGetAllSubscriptionsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Subscription> subscriptions =
        Subscription.all(
            new HashMap<String, Object>() {
              {
                put("plan_id", "plan_tuOWN0Sc0uMB4s8E");
              }
            });
    assertEquals(2, subscriptions.count().intValue());
    assertNotEquals(null, subscriptions.getData());
    List<Subscription> subscriptionlist = subscriptions.getData();
    assertEquals(1, subscriptionlist.size());
  }

  @Test
  public void testGetAllSubscriptionsWithCountParam() throws PayabbhiException {
    PayabbhiCollection<Subscription> subscriptions =
        Subscription.all(
            new HashMap<String, Object>() {
              {
                put("count", 1);
              }
            });
    assertEquals(2, subscriptions.count().intValue());
    assertNotEquals(null, subscriptions.getData());
    List<Subscription> subscriptionlist = subscriptions.getData();
    assertEquals(1, subscriptionlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllSubscriptionsWithInvalidFilters() throws PayabbhiException {

    Subscription.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveSubscriptionDetails() throws PayabbhiException {
    Subscription subscription = Subscription.retrieve("sub_luQ4QIXzaEIN0g5D");
    assertEquals("sub_luQ4QIXzaEIN0g5D", subscription.get("id"));
    assertEquals("plan_tuOWN0Sc0uMB4s8E", subscription.get("plan_id"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", subscription.get("customer_id"));
    assertEquals("recurring", subscription.get("billing_method"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveSubscriptionWithInvalidID() throws PayabbhiException {
    Subscription.retrieve("sub_invalid");
  }

  @Test
  public void testCreateNewSubscription() throws PayabbhiException {
    Subscription subscription =
        Subscription.create(
            new HashMap<String, Object>() {
              {
                put("plan_id", "plan_tuOWN0Sc0uMB4s8E");
                put("billing_cycle_count", 5);
                put("customer_id", "cust_2WmsQoSRZMWWkcZg");
              }
            });
    assertEquals("sub_luQ4QIXzaEIN0g5D", subscription.get("id"));
    assertEquals("plan_tuOWN0Sc0uMB4s8E", subscription.get("plan_id"));
    assertEquals((Integer) 5, subscription.get("billing_cycle_count"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", subscription.get("customer_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewSubscriptionWithLessParams() throws PayabbhiException {
    Subscription.create(
        new HashMap<String, Object>() {
          {
            put("customer_id", "cust_2WmsQoSRZMWWkcZg");
          }
        });
  }

  @Test
  public void testCancelSubscriptionWithOption() throws PayabbhiException {
    Subscription subscription =
        Subscription.cancel(
            "sub_xLH108FJwUlX47SI",
            new HashMap<String, Object>() {
              {
                put("at_billing_cycle_end", true);
              }
            });
    assertEquals("sub_xLH108FJwUlX47SI", subscription.get("id"));
    assertEquals(true, subscription.get("cancel_at_period_end"));
    assertEquals("active", subscription.get("status"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCancelSubscriptionWithInvalidOption() throws PayabbhiException {
    Subscription.cancel(
        "sub_luQ4QIXzaEIN0g5D",
        new HashMap<String, Object>() {
          {
            put("cycle_end", true);
          }
        });
  }

  @Test
  public void testCancelSubscription() throws PayabbhiException {
    Subscription subscription = Subscription.cancel("sub_luQ4QIXzaEIN0g5D");
    assertEquals("sub_luQ4QIXzaEIN0g5D", subscription.get("id"));
    assertEquals(false, subscription.get("cancel_at_period_end"));
    assertEquals("cancelled", subscription.get("status"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCancelSubscriptionWithInvalidID() throws PayabbhiException {
    Subscription.cancel("sub_invalid");
  }
}
