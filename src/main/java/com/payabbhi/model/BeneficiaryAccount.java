package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class BeneficiaryAccount extends APIResource {
  public BeneficiaryAccount(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of beneficiary accounts created previously.
   *
   * @return a collection of beneficiary account objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<BeneficiaryAccount> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(BeneficiaryAccount.class), null, BeneficiaryAccount.class);
  }

  /**
   * Returns a list of beneficiary account created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of beneficiary account objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<BeneficiaryAccount> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(BeneficiaryAccount.class), params), null, BeneficiaryAccount.class);
  }

  /**
   * Returns an beneficiary account object matching the given beneficiary account id.
   *
   * @param id an identifier of the beneficiary account object to retrieve
   * @return an beneficiary account object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static BeneficiaryAccount retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(BeneficiaryAccount.class, id), null, BeneficiaryAccount.class);
  }

  /**
   * Creates a new beneficiary account.
   *
   * @param params a map consisting of parameters used for creating an beneficiary account object:
   * @return the newly created beneficiary account object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static BeneficiaryAccount create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(BeneficiaryAccount.class), params, BeneficiaryAccount.class);
  }
}
