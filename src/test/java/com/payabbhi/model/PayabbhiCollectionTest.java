package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class PayabbhiCollectionTest extends BaseTest {
  private JSONObject ordersJSONObject;
  private JSONObject orderJSONObject;

  @Before
  public void setUp() throws JSONException, IOException {
    ordersJSONObject = new JSONObject(getResourceAsString("/collections/orders.json"));
    orderJSONObject = new JSONObject(getResourceAsString("/collections/order.json"));
  }

  @Test
  public void testPayabbhiCollectionJSONObject() throws JSONException, IOException {

    PayabbhiCollection<Order> orders = new PayabbhiCollection<>(ordersJSONObject);
    assertNotNull(orders);
  }

  @Test
  public void testPayabbhiCollectionWithCount() {
    PayabbhiCollection<Order> orders = new PayabbhiCollection<Order>(ordersJSONObject, 563);
    assertNotNull(orders);
    assertEquals(563, orders.count().intValue());
  }

  @Test
  public void testGetData() {
    PayabbhiCollection<Order> orders = new PayabbhiCollection<Order>(ordersJSONObject, 563);
    List<Order> list = orders.getData();
    assertNotNull(list);
  }

  @Test
  public void testCount() {
    PayabbhiCollection<Order> orders = new PayabbhiCollection<Order>(ordersJSONObject, 563);
    assertEquals(563, orders.count().intValue());
  }

  @Test
  public void testAdd() {
    PayabbhiCollection<Order> orders = new PayabbhiCollection<Order>(ordersJSONObject, 563);
    orders.add(new Order(orderJSONObject));
    assertEquals(1, orders.getData().size());
  }
}
