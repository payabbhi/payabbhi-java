package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;

@SuppressWarnings("serial")
public class PayoutTest extends BaseTest {

	/** It setups the payabbhi object with accessID and secretKey. */
	@Before
	public void setUp() throws Exception {
		Payabbhi.accessId = "some_access_id";
		Payabbhi.secretKey = "some_secret_key";

		mockFetcher();
	}

	@Test
	public void testGetAllPayouts() throws PayabbhiException {
		PayabbhiCollection<Payout> payouts = Payout.all();
		assertEquals(3, payouts.count().intValue());
		assertNotEquals(null, payouts.getData());
	}

	@Test
	public void testGetAllPayoutsWithCountParam() throws PayabbhiException {
		PayabbhiCollection<Payout> payouts = Payout.all(new HashMap<String, Object>() {
			{
				put("count", 2);
			}
		});
		assertEquals(3, payouts.count().intValue());
		assertNotEquals(null, payouts.getData());
		List<Payout> payoutlist = payouts.getData();
		assertEquals(2, payoutlist.size());
	}

	@Test(expected = PayabbhiException.class)
	public void testGetAllPayoutsWithInvalidFilters() throws PayabbhiException {

		Payout.all(new HashMap<String, Object>() {
			{
				put("count", null);
			}
		});
	}

	@Test
	public void testRetrievePayoutDetails() throws PayabbhiException {
		Payout payout = Payout.retrieve("pout_DBzMRrGfIQtHoBs7");
		assertEquals("pout_DBzMRrGfIQtHoBs7", payout.get("id"));
	}

	@Test(expected = PayabbhiException.class)
	public void testRetrievePayoutWithInvalidID() throws PayabbhiException {
		Payout.retrieve("payout_invalid");
	}

	@Test
	public void testCreateNewPayout() throws PayabbhiException {
		Map<String, Object> payoutReqPayload = new TreeMap<>();

		payoutReqPayload.put("amount", 1000);
		payoutReqPayload.put("currency", "INR");
		payoutReqPayload.put("merchant_reference_id", "ref_00001");
		payoutReqPayload.put("remittance_account_no", "1234567890");
		payoutReqPayload.put("beneficiary_account_no", "01234567890");
		payoutReqPayload.put("beneficiary_ifsc", "ABCD1234567");
		payoutReqPayload.put("beneficiary_name", "BenTest");
		payoutReqPayload.put("method", "bank_transfer");
		payoutReqPayload.put("purpose", "cashback");
		payoutReqPayload.put("narration", "info");
		payoutReqPayload.put("instrument", "NEFT");

		Payout payout = Payout.create(payoutReqPayload);
		assertEquals("pout_DBzMRrGfIQtHoBs7", payout.get("id"));
		assertEquals((Integer) 1000, payout.get("amount"));
		assertEquals("INR", payout.get("currency"));
	}

	@Test(expected = PayabbhiException.class)
	public void testCreateNewPayoutWithLessParams() throws PayabbhiException {
		Payout.create(new HashMap<String, Object>() {
			{
				put("amount", 1000);
				put("currency", "INR");
			}
		});
	}
}
