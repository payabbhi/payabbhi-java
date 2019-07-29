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
public class InvoiceItemTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllInvoiceItems() throws PayabbhiException {
    PayabbhiCollection<InvoiceItem> invoiceitems = InvoiceItem.all();
    assertEquals(2, invoiceitems.count().intValue());
    assertNotEquals(null, invoiceitems.getData());
  }

  @Test
  public void testGetAllInvoiceItemsWithFilters() throws PayabbhiException {
    PayabbhiCollection<InvoiceItem> invoiceitems =
        InvoiceItem.all(
            new HashMap<String, Object>() {
              {
                put("count", 1);
              }
            });
    assertEquals(2, invoiceitems.count().intValue());
    assertNotEquals(null, invoiceitems.getData());
    List<InvoiceItem> invoiceitemlist = invoiceitems.getData();
    assertEquals(1, invoiceitemlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllInvoiceItemsWithInvalidFilters() throws PayabbhiException {

    InvoiceItem.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveInvoiceItemDetails() throws PayabbhiException {
    InvoiceItem invoiceitem = InvoiceItem.retrieve("item_zvenYE0Tk8qTUaER");
    assertEquals("item_zvenYE0Tk8qTUaER", invoiceitem.get("id"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", invoiceitem.get("customer_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveInvoiceItemWithInvalidID() throws PayabbhiException {
    InvoiceItem.retrieve("item_invalid");
  }

  @Test
  public void testCreateNewInvoiceItem() throws PayabbhiException {
    InvoiceItem invoiceitem =
        InvoiceItem.create(
            new HashMap<String, Object>() {
              {
                put("name", "Line Item");
                put("amount", 100);
                put("currency", "INR");
                put("customer_id", "cust_2WmsQoSRZMWWkcZg");
              }
            });
    assertEquals("item_OQ4jsxy3aMwYE9T7", invoiceitem.get("id"));
    assertEquals("Line Item", invoiceitem.get("name"));
    assertEquals((Integer) 200, invoiceitem.get("amount"));
    assertEquals("INR", invoiceitem.get("currency"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", invoiceitem.get("customer_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewInvoiceItemWithLessParams() throws PayabbhiException {
    InvoiceItem.create(
        new HashMap<String, Object>() {
          {
            put("name", "Line Item");
            put("amount", 100);
          }
        });
  }

  @Test
  public void testDeleteInvoiceItem() throws PayabbhiException {
    InvoiceItem invoiceitem = InvoiceItem.delete("item_zvenYE0Tk8qTUaER");
    assertEquals("item_2MAlPM205eXk65Fx", invoiceitem.get("id"));
    assertEquals("N3", invoiceitem.get("name"));
    assertEquals((Integer) 200, invoiceitem.get("amount"));
    assertEquals("INR", invoiceitem.get("currency"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", invoiceitem.get("customer_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testDeleteInvoiceItemWithInvalidID() throws PayabbhiException {
    InvoiceItem.delete("item_invalid");
  }

  @Test
  public void testRetrieveInvoicesOfItem() throws PayabbhiException {
    PayabbhiCollection<Invoice> invoices = InvoiceItem.invoices("item_KI75WbBlizfzLZui");
    assertEquals(2, invoices.count().intValue());
    assertNotEquals(null, invoices.getData());
    List<Invoice> invoicesData = invoices.getData();
    assertEquals(2, invoicesData.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveLineInvoicesOfItemWithInvalidItemID() throws PayabbhiException {
    InvoiceItem.invoices("item_invalid");
  }
}
