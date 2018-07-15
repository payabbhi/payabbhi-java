package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Payment extends APIResource {
  public Payment(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of payments created previously
   *
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> all() throws PayabbhiException {
    return all(null);
  }

  /**
   * Returns a list of payments created previously
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Payment.class), params), null, Payment.class);
  }

  /**
   * Returns a payment object matching the given payment id
   *
   * @param id an identifier of the payment object to retrieve
   * @return a payment object
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static Payment retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Payment.class, id), null, Payment.class);
  }

  /**
   * Captures an existing, un-captured payment.
   *
   * @return the payment object, with a status attribute set as captured
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public Payment capture() throws PayabbhiException {
    return capture(null);
  }

  /**
   * Captures an existing, un-captured payment.
   *
   * @param params a map of optional parameters
   * @return the payment object, with a status attribute set as captured
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public Payment capture(Map<String, Object> params) throws PayabbhiException {
    String id = this.get("id");
    Payment payment =
        request(Method.POST, urlFor(Payment.class, id, "capture"), null, Payment.class);
    this.setModelJson(payment.getModelJson());
    return payment;
  }

  /**
   * Returns a list of refunds for a given payment that have been previously created
   *
   * @param id the identifier of the payment whose refunds are to be retrieved
   * @return a collection of refund objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Refund> refunds(String id) throws PayabbhiException {
    return refunds(id, null);
  }

  /**
   * Returns a list of refunds for a given payment that have been previously created
   *
   * @param id the identifier of the payment whose refunds are to be retrieved
   * @param params a map of optional parameters to refine the search
   * @return a collection of refund objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Refund> refunds(String id, Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET,
        withParams(urlFor(Payment.class, id, Refund.class), params),
        null,
        Refund.class);
  }
}
