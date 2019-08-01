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
public class BeneficiaryAccountTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllBeneficiaryAccounts() throws PayabbhiException {
    PayabbhiCollection<BeneficiaryAccount> beneficiaryAccounts = BeneficiaryAccount.all();
    assertEquals(3, beneficiaryAccounts.count().intValue());
    assertNotEquals(null, beneficiaryAccounts.getData());
  }

  @Test
  public void testGetAllBeneficiaryAccountsWithFilters() throws PayabbhiException {
    PayabbhiCollection<BeneficiaryAccount> beneficiaryaccounts =
        BeneficiaryAccount.all(
            new HashMap<String, Object>() {
              {
                put("count", 2);
              }
            });
    assertEquals(2, beneficiaryaccounts.count().intValue());
    assertNotEquals(null, beneficiaryaccounts.getData());
    List<BeneficiaryAccount> beneficiaryaccountslist = beneficiaryaccounts.getData();
    assertEquals(2, beneficiaryaccountslist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllBeneficiaryAccountsWithInvalidFilters() throws PayabbhiException {

    BeneficiaryAccount.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveBeneficiaryAccountDetails() throws PayabbhiException {
    BeneficiaryAccount beneficiaryaccount = BeneficiaryAccount.retrieve("bene_d7d8b37d264c4264");
    assertEquals("bene_d7d8b37d264c4264", beneficiaryaccount.get("id"));
    assertEquals("ui_test", beneficiaryaccount.get("name"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveBeneficiaryAccountWithInvalidID() throws PayabbhiException {
    BeneficiaryAccount.retrieve("bene_invalid");
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewBeneficiaryAccountWithoutMandatoryParam() throws PayabbhiException {
	  BeneficiaryAccount.create(
        new HashMap<String, Object>() {
          {
            put("business_name", "Paypermint Pvt Ltd");
          }
        });
  }
}

