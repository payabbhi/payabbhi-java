package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Product extends APIResource {
  public Product(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of products created previously.
   *
   * @return a collection of product objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Product> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Product.class), null, Product.class);
  }

  /**
   * Returns a list of products created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of product objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Product> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Product.class), params), null, Product.class);
  }

  /**
   * Returns an product object matching the given product id.
   *
   * @param id an identifier of the product object to retrieve
   * @return an product object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Product retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Product.class, id), null, Product.class);
  }

  /**
   * Creates a new product.
   *
   * @param params a map consisting of parameters used for creating an product object:
   * @return the newly created product object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Product create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Product.class), params, Product.class);
  }
}
