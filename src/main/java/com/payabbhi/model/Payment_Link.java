package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Payment_Link extends APIResource {
  public Payment_Link(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of payment links created previously.
   *
   * @return a collection of payment link objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Payment_Link> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Payment_Link.class), null, Payment_Link.class);
  }

  /**
   * Returns a list of payment links created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of payment link objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Payment_Link> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Payment_Link.class), params), null, Payment_Link.class);
  }

  /**
   * Returns an payment link object matching the given payment link id.
   *
   * @param id an identifier of the payment link object to retrieve
   * @return an payment link object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Payment_Link retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Payment_Link.class, id), null, Payment_Link.class);
  }

  /**
   * Creates a new Payment Link.
   *
   * @param params a map consisting of parameters used for creating an payment link object:
   * @return the newly created payment link object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Payment_Link create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Payment_Link.class), params, Payment_Link.class);
  }

  /**
   * Cancels an payment link.
   *
   * @param id an identifier of the payment link object to cancel
   * @return an payment link object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Payment_Link cancel(String id) throws PayabbhiException {
    return request(Method.POST, urlFor(Payment_Link.class, id, "cancel"), null, Payment_Link.class);
  }

  /**
   * Returns a list of all payments for a given payment link that have previously been created.
   *
   * @param id the identifier of the payment link whose payments are to be retrieved
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id) throws PayabbhiException {
    return payments(id, null);
  }

  /**
   * Returns a list of all payments for a given payment link that have previously been created.
   *
   * @param id the identifier of the payment link whose payments are to be retrieved
   * @param params A map of optional parameters to refine the search
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id, Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET,
        withParams(urlFor(Payment_Link.class, id, Payment.class), params),
        null,
        Payment.class);
  }
}
