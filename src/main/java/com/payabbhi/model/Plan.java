package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Plan extends APIResource {
  public Plan(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of plans created previously.
   *
   * @return a collection of plan objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Plan> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Plan.class), null, Plan.class);
  }

  /**
   * Returns a list of plans created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of plan objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Plan> all(Map<String, Object> params) throws PayabbhiException {
    return requestCollection(Method.GET, withParams(urlFor(Plan.class), params), null, Plan.class);
  }

  /**
   * Returns an plan object matching the given plan id.
   *
   * @param id an identifier of the plan object to retrieve
   * @return an plan object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Plan retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Plan.class, id), null, Plan.class);
  }

  /**
   * Creates a new plan.
   *
   * @param params a map consisting of parameters used for creating an plan object:
   * @return the newly created plan object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Plan create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Plan.class), params, Plan.class);
  }
}
