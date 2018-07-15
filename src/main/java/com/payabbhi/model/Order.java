package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Order extends APIResource {
  public Order(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of orders created previously.
   *
   * @return a collection of order objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Order> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Order.class), null, Order.class);
  }

  /**
   * @param params a map of optional parameters to refine the search
   * @return a collection of order objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Order> all(Map<String, Object> params) throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Order.class), params), null, Order.class);
  }

  /**
   * Returns an order object matching the given order id
   *
   * @param id an identifier of the order object to retrieve
   * @return an order object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Order retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Order.class, id), null, Order.class);
  }

  /**
   * Creates a new order.
   *
   * @param params a map consisting of parameters used for creating an order object:
   * @return the newly created order object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Order create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Order.class), params, Order.class);
  }

  /**
   * Returns a list of all payments for a given order that have previously been created.
   *
   * @param id the identifier of the order whose payments are to be retrieved
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id) throws PayabbhiException {
    return payments(id, null);
  }

  /**
   * Returns a list of all payments for a given order that have previously been created.
   *
   * @param id the identifier of the order whose payments are to be retrieved
   * @param params A map of optional parameters to refine the search
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id, Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET,
        withParams(urlFor(Order.class, id, Payment.class), params),
        null,
        Payment.class);
  }
}
