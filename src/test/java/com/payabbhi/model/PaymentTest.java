package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class PaymentTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllPayments() throws PayabbhiException {
    PayabbhiCollection<Payment> payments = Payment.all();
    assertEquals(171, payments.count().intValue());
    assertNotEquals(null, payments.getData());
  }

  @Test
  public void testGetAllPaymentsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Payment> payments =
        Payment.all(
            new HashMap<String, Object>() {
              {
                put("from", 1531208000);
              }
            });
    assertEquals(1, payments.count().intValue());
    assertNotEquals(null, payments.getData());
    List<Payment> paylist = payments.getData();
    assertEquals(1, paylist.size());
  }

  @Test
  public void testRetrievePaymentDetails() throws PayabbhiException {
    Payment payinfo = Payment.retrieve("pay_C5OcIOxiCrZXAcHG");
    assertEquals("pay_C5OcIOxiCrZXAcHG", payinfo.get("id"));
    assertEquals("order_8j3N11lCbJ2NtzOd", payinfo.get("order_id"));
  }

  @Test
  public void testCapturePayment() throws PayabbhiException {
    Payment payinfo = Payment.retrieve("pay_C5OcIOxiCrZXAcHG");
    assertEquals("authorized", payinfo.get("status"));
    payinfo.capture();
    assertEquals("captured", payinfo.get("status"));
    assertEquals("pay_C5OcIOxiCrZXAcHG", payinfo.get("id"));
  }

  @Test
  public void testRetrieveRefundsOfPayment() throws PayabbhiException {
    PayabbhiCollection<Refund> refunds = Payment.refunds("pay_R6mPqlzzukJTgWbS");
    assertEquals(3, refunds.count().intValue());
    assertNotEquals(null, refunds.getData());
    List<Refund> paylist = refunds.getData();
    assertEquals(1, paylist.size());
  }

  @Test
  public void testRetrieveTransfersOfPayment() throws PayabbhiException {
    PayabbhiCollection<Transfer> transfers = Payment.transfers("pay_zlAsx5g7yH88xcFE");
    assertEquals(2, transfers.count().intValue());
    assertNotEquals(null, transfers.getData());
    List<Transfer> paylist = transfers.getData();
    assertEquals(2, paylist.size());
  }
  
  @Test
  public void testCreateNewTransfer() throws PayabbhiException {
    Map<String, Object> transfer1 = new HashMap<>();
    transfer1.put("beneficiary_id", "bene_d7d8b37d264c4264");
    transfer1.put("amount", 20);
    transfer1.put("currency", "INR");

    List<Object> tranfersPayload = new ArrayList<>();
    tranfersPayload.add(transfer1);

    Map<String, Object> options = new HashMap<>();
    options.put("transfers", tranfersPayload);

    PayabbhiCollection<Transfer> transfer = Payment.transfer("pay_zlAsx5g7yH88xcFE", options);
    JSONArray dataArr = (JSONArray) transfer.get("data");
    JSONObject transferObj = dataArr.getJSONObject(0);
    assertEquals("trans_b5ggPH8LUbfe0Qfg", transferObj.getString("id"));
    assertEquals((Integer) 20, transferObj.getNumber("amount"));
    assertEquals("INR", transferObj.getString("currency"));
    assertEquals("pay_zlAsx5g7yH88xcFE", transferObj.getString("source_id"));
    assertEquals("bene_d7d8b37d264c4264", transferObj.getString("beneficiary_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewTransferWithLessParams() throws PayabbhiException {
    Payment.transfer(
        "pay_zlAsx5g7yH88xcFE",
        new HashMap<String, Object>() {
          {
            put("beneficiary_id", "bene_invalidId");
          }
        });
  }
}
