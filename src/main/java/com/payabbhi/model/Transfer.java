package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
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
   * Creates a new transfer.
   *
   * @param params a map consisting of parameters used for creating an transfer object:
   * @return the newly created transfer object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Transfer create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Transfer.class), params, Transfer.class);
  }
}
