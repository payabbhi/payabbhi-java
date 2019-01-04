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
public class TransferTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllTransfers() throws PayabbhiException {
    PayabbhiCollection<Transfer> transfers = Transfer.all();
    assertEquals(3, transfers.count().intValue());
    assertNotEquals(null, transfers.getData());
  }

  @Test
  public void testGetAllTransfersWithFilters() throws PayabbhiException {
    PayabbhiCollection<Transfer> transfers =
        Transfer.all(
            new HashMap<String, Object>() {
              {
                put("count", 2);
              }
            });
    assertEquals(3, transfers.count().intValue());
    assertNotEquals(null, transfers.getData());
    List<Transfer> transferlist = transfers.getData();
    assertEquals(2, transferlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllTransfersWithInvalidFilters() throws PayabbhiException {

    Transfer.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveTransferDetails() throws PayabbhiException {
    Transfer transfer = Transfer.retrieve("trans_ucwszWrXUZJGDgMX");
    assertEquals("trans_ucwszWrXUZJGDgMX", transfer.get("id"));
    assertEquals("pay_W2FmbqANt09epUOz", transfer.get("source_id"));
    assertEquals("recp_Y2ojRlJVqRMhB0Ay", transfer.get("recipient_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveTransferWithInvalidID() throws PayabbhiException {
    Transfer.retrieve("trans_invalid");
  }

  @Test
  public void testCreateNewTransfer() throws PayabbhiException {
    Transfer transfer =
        Transfer.create(
            new HashMap<String, Object>() {
              {
                put("source_id", "pay_W2FmbqANt09epUOz");
                put("recipient_id", "recp_Y2ojRlJVqRMhB0Ay");
                put("amount", 50);
                put("currency", "INR");
              }
            });
    assertEquals("trans_ucwszWrXUZJGDgMX", transfer.get("id"));
    assertEquals((Integer) 50, transfer.get("amount"));
    assertEquals("INR", transfer.get("currency"));
    assertEquals("pay_W2FmbqANt09epUOz", transfer.get("source_id"));
    assertEquals("recp_Y2ojRlJVqRMhB0Ay", transfer.get("recipient_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewTransferWithLessParams() throws PayabbhiException {
    Transfer.create(
        new HashMap<String, Object>() {
          {
            put("source_id", "pay_W2FmbqANt09epUOz");
            put("recipient_id", "recp_Y2ojRlJVqRMhB0Ay");
          }
        });
  }
}
