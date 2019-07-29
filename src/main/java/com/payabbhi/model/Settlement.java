package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Settlement extends APIResource {
  public Settlement(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of settlements created previously.
   *
   * @return a collection of settlement objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Settlement> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Settlement.class), null, Settlement.class);
  }

  /**
   * Returns a list of settlements created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of settlement objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Settlement> all(Map<String, Object> params) throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Settlement.class), params), null, Settlement.class);
  }

  /**
   * Returns an settlement object matching the given settlement id.
   *
   * @param id an identifier of the settlement object to retrieve
   * @return an settlement object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Settlement retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Settlement.class, id), null, Settlement.class);
  }
}
