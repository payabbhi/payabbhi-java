package com.payabbhi.model;

import java.util.Map;

import org.json.JSONObject;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;

public class Payout extends APIResource {
	public Payout(JSONObject jsonObject) {
		super(jsonObject);
	}

	/**
	 * Returns a list of payouts created previously.
	 *
	 * @return a collection of payout objects
	 * @throws PayabbhiException if there is a problem in performing the operation.
	 */
	public static PayabbhiCollection<Payout> all() throws PayabbhiException {
		return requestCollection(Method.GET, urlFor(Payout.class), null, Payout.class);
	}

	/**
	 * Returns a list of payouts created previously with parameters.
	 *
	 * @param params a map of optional parameters to refine the search
	 * @return a collection of plan objects
	 * @throws PayabbhiException if there is a problem in performing the operation.
	 */
	public static PayabbhiCollection<Payout> all(Map<String, Object> params) throws PayabbhiException {
		return requestCollection(Method.GET, withParams(urlFor(Payout.class), params), null, Payout.class);
	}

	/**
	 * Returns a payout object matching the given payout id.
	 *
	 * @param id an identifier of the plan object to retrieve
	 * @return a payout object
	 * @throws PayabbhiException if there is a problem in performing the operation.
	 */
	public static Payout retrieve(String id) throws PayabbhiException {
		return request(Method.GET, urlFor(Payout.class, id), null, Payout.class);
	}

	/**
	 * Creates a new payout.
	 *
	 * @param params a map consisting of parameters used for creating a payout
	 *               object:
	 * @return the newly created payout object
	 * @throws PayabbhiException if there is a problem in performing the operation.
	 */
	public static Payout create(Map<String, Object> params) throws PayabbhiException {
//		System.out.println(toJSONString(params));
		return request(Method.POST, urlFor(Payout.class), params, Payout.class);
	}
}
