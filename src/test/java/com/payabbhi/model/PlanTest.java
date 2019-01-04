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
public class PlanTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllPlans() throws PayabbhiException {
    PayabbhiCollection<Plan> plans = Plan.all();
    assertEquals(3, plans.count().intValue());
    assertNotEquals(null, plans.getData());
  }

  @Test
  public void testGetAllPlansWithFilters() throws PayabbhiException {
    PayabbhiCollection<Plan> plans =
        Plan.all(
            new HashMap<String, Object>() {
              {
                put("product_id", "prod_wJ6DyX5Bgg2LqAqt");
              }
            });
    assertEquals(3, plans.count().intValue());
    assertNotEquals(null, plans.getData());
    List<Plan> planlist = plans.getData();
    assertEquals(2, planlist.size());
  }

  @Test
  public void testGetAllPlansWithCountParam() throws PayabbhiException {
    PayabbhiCollection<Plan> plans =
        Plan.all(
            new HashMap<String, Object>() {
              {
                put("count", 2);
              }
            });
    assertEquals(3, plans.count().intValue());
    assertNotEquals(null, plans.getData());
    List<Plan> planlist = plans.getData();
    assertEquals(2, planlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllPlansWithInvalidFilters() throws PayabbhiException {

    Plan.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrievePlanDetails() throws PayabbhiException {
    Plan plan = Plan.retrieve("plan_P7wNUwTdGC2u2n2I");
    assertEquals("plan_P7wNUwTdGC2u2n2I", plan.get("id"));
    assertEquals("prod_wJ6DyX5Bgg2LqAqt", plan.get("product_id"));
    assertEquals("month(s)", plan.get("interval"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrievePlanWithInvalidID() throws PayabbhiException {
    Plan.retrieve("plan_invalid");
  }

  @Test
  public void testCreateNewPlan() throws PayabbhiException {
    Plan plan =
        Plan.create(
            new HashMap<String, Object>() {
              {
                put("product_id", "prod_wJ6DyX5Bgg2LqAqt");
                put("amount", 100);
                put("currency", "INR");
                put("frequency", 2);
                put("interval", "month(s)");
              }
            });
    assertEquals("plan_P7wNUwTdGC2u2n2I", plan.get("id"));
    assertEquals("prod_wJ6DyX5Bgg2LqAqt", plan.get("product_id"));
    assertEquals((Integer) 100, plan.get("amount"));
    assertEquals("INR", plan.get("currency"));
    assertEquals((Integer) 2, plan.get("frequency"));
    assertEquals("month(s)", plan.get("interval"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewPlanWithLessParams() throws PayabbhiException {
    Plan.create(
        new HashMap<String, Object>() {
          {
            put("product_id", "prod_wJ6DyX5Bgg2LqAqt");
            put("amount", 100);
            put("currency", "INR");
          }
        });
  }
}
