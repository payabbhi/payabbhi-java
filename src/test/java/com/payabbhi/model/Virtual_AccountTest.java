package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class Virtual_AccountTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllVirtual_Accounts() throws PayabbhiException {
    PayabbhiCollection<Virtual_Account> virtual_accounts = Virtual_Account.all();
    assertEquals(2, virtual_accounts.count().intValue());
    assertNotEquals(null, virtual_accounts.getData());
  }

  @Test
  public void testGetAllVirtual_AccountsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Virtual_Account> virtual_accounts =
        Virtual_Account.all(
            new HashMap<String, Object>() {
              {
                put("count", 1);
              }
            });
    assertEquals(2, virtual_accounts.count().intValue());
    assertNotEquals(null, virtual_accounts.getData());
    List<Virtual_Account> virtual_accountslist = virtual_accounts.getData();
    assertEquals(1, virtual_accountslist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllVirtual_AccountsWithInvalidFilters() throws PayabbhiException {

    Virtual_Account.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveVirtual_AccountDetails() throws PayabbhiException {
	  Virtual_Account virtual_account = Virtual_Account.retrieve("va_FMkEnEGEmHhMKZzL");
    assertEquals("va_FMkEnEGEmHhMKZzL", virtual_account.get("id"));
    assertEquals("cust_2BCT1Tvgg1gNjVPu", virtual_account.get("customer_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveVirtual_AccountWithInvalidID() throws PayabbhiException {
	  Virtual_Account.retrieve("va_invalid");
  }

  @Test
  public void testCloseVirtual_Account() throws PayabbhiException {
	  Virtual_Account virtual_account = Virtual_Account.close("va_FMkEnEGEmHhMKZzL");
    assertEquals("va_FMkEnEGEmHhMKZzL", virtual_account.get("id"));
    assertEquals("closed", virtual_account.get("status"));
    assertEquals((Integer) 200000, virtual_account.get("paid_amount"));
    assertEquals("cust_2BCT1Tvgg1gNjVPu", virtual_account.get("customer_id"));
  }
  
  @Test
  public void testListPaymentsOfVirtual_Account() throws PayabbhiException {
    PayabbhiCollection<Payment> payments = Virtual_Account.payments("va_PakYenlyIIPjGwoU");
    assertEquals(2, payments.count().intValue());
    assertNotEquals(null, payments.getData());
  }
  
  @Test(expected = PayabbhiException.class)
  public void testListPaymentsOfVirtual_AccountWithInvalidVirtual_AccountID() throws PayabbhiException {
    Virtual_Account.payments("va_invalid");
  }
  
  @Test
  public void testRetrieveVirtualAccountDetailsTail() throws PayabbhiException {
    Payment payments = Payment.virtual_account("pay_4I4NDogajGtV9bVo");
    assertEquals("pay_4I4NDogajGtV9bVo", payments.get("id"));
    assertEquals("bank_account", payments.get("method"));
  }

}
