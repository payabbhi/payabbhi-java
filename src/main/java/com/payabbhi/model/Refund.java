package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Refund extends APIResource {
  public Refund(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a refund object matching the refund_id
   *
   * @param id The identifier of the refund to be retrieved
   * @return a refund object
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static Refund retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Refund.class, id), null, Refund.class);
  }

  /**
   * Returns a list of refunds created previously
   *
   * @return a collection of refund objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Refund> all() throws PayabbhiException {
    return all(null);
  }

  /**
   * Returns a list of refunds created previously
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of refund objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Refund> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Refund.class), params), null, Refund.class);
  }

  /**
   * create a refund for a previously captured payment that is not yet fully refunded
   *
   * @param id The identifier of the payment for which refund is being created
   * @return the refund object if the refund is successful
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static Refund create(String id) throws PayabbhiException {
    return create(id, null);
  }

  /**
   * create a refund for a previously captured payment that is not yet fully refunded
   *
   * @param paymentId The identifier of the payment for which refund is being created
   * @param params a map of optional parameters
   * @return the refund object if the refund is successful
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static Refund create(String paymentId, Map<String, Object> params)
      throws PayabbhiException {
    return request(
        Method.POST, urlFor(Payment.class, paymentId, Refund.class), params, Refund.class);
  }
}
