package com.payabbhi.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;

@SuppressWarnings("serial")
public class Remittance_AccountTest extends BaseTest {

	/** It setups the payabbhi object with accessID and secretKey. */
	@Before
	public void setUp() throws Exception {
		Payabbhi.accessId = "some_access_id";
		Payabbhi.secretKey = "some_secret_key";

		mockFetcher();
	}

	@Test
	public void testRetrieveRemittance_AccountDetails() throws PayabbhiException {
		Remittance_Account remittance_account = Remittance_Account.retrieve("123456789012345");
		assertEquals("123456789012345", remittance_account.get("id"));
	}

	@Test(expected = PayabbhiException.class)
	public void testRetrieveRemittance_AccountWithInvalidID() throws PayabbhiException {
		Remittance_Account.retrieve("ra_invalid");
	}

}
