package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Virtual_Account extends APIResource {
  public Virtual_Account(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of virtual accounts created previously.
   *
   * @return a collection of virtual_accounts objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Virtual_Account> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Virtual_Account.class), null, Virtual_Account.class);
  }

  /**
   * Returns a list of virtual accounts created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of virtual_accounts objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Virtual_Account> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Virtual_Account.class), params), null, Virtual_Account.class);
  }

  /**
   * Returns an virtual account object matching the given virtual account id.
   *
   * @param id an identifier of the virtual_account object to retrieve
   * @return an virtual_account object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Virtual_Account retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Virtual_Account.class, id), null, Virtual_Account.class);
  }

  /**
   * Creates a new virtual account.
   *
   * @param params a map consisting of parameters used for creating a virtual_account object:
   * @return the newly created virtual_account object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Virtual_Account create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Virtual_Account.class), params, Virtual_Account.class);
  }
  
  /**
   * Delete a new virtual account.
   *
   * @param id a identifier for closing a virtual_account object:
   * @return the virtual_account object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Virtual_Account close(String id) throws PayabbhiException {
    return request(Method.PATCH, urlFor(Virtual_Account.class, id), null, Virtual_Account.class);
  }

  /**
   * Returns a list of all payments for a given virtual account that have previously been created.
   *
   * @param id the identifier of the virtual_account whose payments are to be retrieved
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id) throws PayabbhiException {
    return payments(id, null);
  }

  /**
   * Returns a list of all payments for a given virtual account that have previously been created.
   *
   * @param id the identifier of the Virtual_Account whose payments are to be retrieved
   * @param params A map of optional parameters to refine the search
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id, Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET,
        withParams(urlFor(Virtual_Account.class, id, Payment.class), params),
        null,
        Payment.class);
  }
}
