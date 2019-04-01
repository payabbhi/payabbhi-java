package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Subscription extends APIResource {
  public Subscription(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of subscriptions created previously.
   *
   * @return a collection of subscription objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Subscription> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Subscription.class), null, Subscription.class);
  }

  /**
   * Returns a list of subscriptions created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of subscription objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Subscription> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Subscription.class), params), null, Subscription.class);
  }

  /**
   * Returns an subscription object matching the given subscription id.
   *
   * @param id an identifier of the subscription object to retrieve
   * @return an subscription object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Subscription retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Subscription.class, id), null, Subscription.class);
  }

  /**
   * Creates a new subscription.
   *
   * @param params a map consisting of parameters used for creating an subscription object:
   * @return the newly created subscription object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Subscription create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Subscription.class), params, Subscription.class);
  }

  /**
   * Returns an subscription object matching the given subscription id.
   *
   * @param id an identifier of the subscription object to cancel
   * @return an subscription object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Subscription cancel(String id) throws PayabbhiException {
    return cancel(id, null);
  }

  /**
   * Returns an subscription object matching the given subscription id.
   *
   * @param id an identifier of the subscription object to cancel
   * @param params an map of optional parameters
   * @return an subscription object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Subscription cancel(String id, Map<String, Object> params)
      throws PayabbhiException {
    return request(
        Method.POST, urlFor(Subscription.class, id, "cancel"), params, Subscription.class);
  }
}
