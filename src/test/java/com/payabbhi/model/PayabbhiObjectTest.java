package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class PayabbhiObjectTest extends BaseTest {

  private PayabbhiObject payabbhiobject;

  @Before
  public void setUp() throws Exception {
    try {
      payabbhiobject =
          new PayabbhiObject(
              new JSONObject(getResourceAsString("/collections/pay_aIrgi1wnYjT82422.json")));
    } catch (Exception e) {
      payabbhiobject = null;
    }
  }

  @Test
  public void testGetModelJson() {
    assertTrue(payabbhiobject.has("id"));
    assertEquals("pay_aIrgi1wnYjT82422", payabbhiobject.get("id"));
    assertTrue(payabbhiobject.has("order_id"));
    assertEquals("order_dkKfFNBczPHXuagG", payabbhiobject.get("order_id"));
    assertTrue(payabbhiobject.has("created_at"));
    assertEquals(Integer.valueOf(1531299143), payabbhiobject.get("created_at"));
    assertEquals("payment", payabbhiobject.get("object"));
    JSONObject notes = (JSONObject) payabbhiobject.get("notes");
    assertNotNull(notes.get("Name"));
    assertTrue(payabbhiobject.has("refunds"));
    JSONObject refunds = (JSONObject) payabbhiobject.get("refunds");
    assertEquals(Integer.valueOf(2), refunds.get("total_count"));
    JSONArray refundarray = (JSONArray) refunds.get("data");
    assertEquals(2, refundarray.length());

    // Negative Scenario
    assertNull(payabbhiobject.get("random@#$"));
    assertTrue(payabbhiobject.has("emi"));
    assertNull(payabbhiobject.get("emi"));
  }
}
