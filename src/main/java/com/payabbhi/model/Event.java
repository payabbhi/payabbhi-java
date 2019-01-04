package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Event extends APIResource {
  public Event(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of events created previously.
   *
   * @return a collection of event objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Event> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Event.class), null, Event.class);
  }

  /**
   * Returns a list of events created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of event objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Event> all(Map<String, Object> params) throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Event.class), params), null, Event.class);
  }

  /**
   * Returns an event object matching the given event id.
   *
   * @param id an identifier of the event object to retrieve
   * @return an event object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Event retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Event.class, id), null, Event.class);
  }
}
