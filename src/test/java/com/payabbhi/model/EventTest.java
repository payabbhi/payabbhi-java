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
public class EventTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllEvents() throws PayabbhiException {
    PayabbhiCollection<Event> events = Event.all();
    assertEquals(2, events.count().intValue());
    assertNotEquals(null, events.getData());
  }

  @Test
  public void testGetAllEventsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Event> events =
        Event.all(
            new HashMap<String, Object>() {
              {
                put("type", "payment.refunded");
              }
            });
    assertEquals(1, events.count().intValue());
    assertNotEquals(null, events.getData());
    List<Event> eventlist = events.getData();
    assertEquals(1, eventlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllEventsWithInvalidFilters() throws PayabbhiException {

    Event.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveEventDetails() throws PayabbhiException {
    Event event = Event.retrieve("evt_gCmIpp76zgZynfEO");
    assertEquals("evt_gCmIpp76zgZynfEO", event.get("id"));
    assertEquals("payment.captured", event.get("type"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveEventWithInvalidID() throws PayabbhiException {
    Event event = Event.retrieve("evt_invalid");
  }
}
