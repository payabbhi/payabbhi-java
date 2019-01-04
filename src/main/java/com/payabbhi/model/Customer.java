package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Customer extends APIResource {
  public Customer(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of customers created previously.
   *
   * @return a collection of customer objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Customer> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Customer.class), null, Customer.class);
  }

  /**
   * Returns a list of customers created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search.
   * @return a collection of customer objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Customer> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Customer.class), params), null, Customer.class);
  }

  /**
   * Returns an customer object matching the given customer id.
   *
   * @param id an identifier of the customer object to retrieve
   * @return an customer object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Customer retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Customer.class, id), null, Customer.class);
  }

  /**
   * Creates a new customer.
   *
   * @param params a map consisting of parameters used for creating an customer object:
   * @return the newly created customer object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Customer create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Customer.class), params, Customer.class);
  }

  /**
   * Returns a list of all updates for a given customer that have previously been created.
   *
   * @param id the identifier of the customer whose updates are to be retrieved
   * @param params A map of optional parameters to refine the search
   * @return a collection of update objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static Customer edit(String id, Map<String, Object> params) throws PayabbhiException {
    return request(Method.PUT, urlFor(Customer.class, id), params, Customer.class);
  }
}
