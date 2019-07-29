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
public class SettlementTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllSettlements() throws PayabbhiException {
    PayabbhiCollection<Settlement> settlements = Settlement.all();
    assertEquals(2, settlements.count().intValue());
    assertNotEquals(null, settlements.getData());
  }

  @Test
  public void testGetAllSettlementsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Settlement> settlements =
        Settlement.all(
            new HashMap<String, Object>() {
              {
                put("count", "1");
              }
            });
    assertEquals(1, settlements.count().intValue());
    assertNotEquals(null, settlements.getData());
    List<Settlement> settlementlist = settlements.getData();
    assertEquals(1, settlementlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllSettlementsWithInvalidFilters() throws PayabbhiException {
    Settlement.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveSettlementDetails() throws PayabbhiException {
    Settlement settlement = Settlement.retrieve("stl_wmjBUMNyLnzGbFKU");
    assertEquals("stl_wmjBUMNyLnzGbFKU", settlement.get("id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveSettlementWithInvalidID() throws PayabbhiException {
    Settlement settlement = Settlement.retrieve("set_invalid");
  }
}
