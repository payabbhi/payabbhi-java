package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import com.payabbhi.net.APIResource.Method;

import java.util.Map;
import org.json.JSONObject;

public class Transfer extends APIResource {
  public Transfer(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of transfers created previously.
   *
   * @return a collection of transfer objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Transfer> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Transfer.class), null, Transfer.class);
  }

  /**
   * Returns a list of transfers created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of transfer objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Transfer> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Transfer.class), params), null, Transfer.class);
  }

  /**
   * Returns an transfer object matching the given transfer id.
   *
   * @param id an identifier of the transfer object to retrieve
   * @return an transfer object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Transfer retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Transfer.class, id), null, Transfer.class);
  }

  /**
   * Creates new transfers.
   *
   * @param params a map consisting of parameters used for creating transfers object:
   * @return collection of newly created transfer objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Transfer> create(Map<String, Object> params) throws PayabbhiException {
    return requestCollection(Method.POST, urlFor(Transfer.class), params, Transfer.class);
  }
}
