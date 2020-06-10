package com.payabbhi.model;

import org.json.JSONObject;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;

public class Remittance_Account extends APIResource {
	public Remittance_Account(JSONObject jsonObject) {
		super(jsonObject);
	}

	/**
	 * Returns an remittance account object matching the given remittance account
	 * id.
	 *
	 * @param id an identifier of the remittance_account object to retrieve
	 * @return an remittance_account object
	 * @throws PayabbhiException if there is a problem in performing the operation.
	 */
	public static Remittance_Account retrieve(String id) throws PayabbhiException {
		return request(Method.GET, urlFor(Remittance_Account.class, id), null, Remittance_Account.class);
	}
}
