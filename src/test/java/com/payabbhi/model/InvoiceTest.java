package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class InvoiceTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllInvoices() throws PayabbhiException {
    PayabbhiCollection<Invoice> invoices = Invoice.all();
    assertEquals(2, invoices.count().intValue());
    assertNotEquals(null, invoices.getData());
  }

  @Test
  public void testGetAllInvoicesWithFilters() throws PayabbhiException {
    PayabbhiCollection<Invoice> invoices =
        Invoice.all(
            new HashMap<String, Object>() {
              {
                put("count", 1);
              }
            });
    assertEquals(2, invoices.count().intValue());
    assertNotEquals(null, invoices.getData());
    List<Invoice> invoicelist = invoices.getData();
    assertEquals(1, invoicelist.size());
  }

  @Test
  public void testGetAllInvoicesWithSubscriptionID() throws PayabbhiException {
    PayabbhiCollection<Invoice> invoices =
        Invoice.all(
            new HashMap<String, Object>() {
              {
                put("subscription_id", "sub_kds3f349dsl4dk9x");
              }
            });
    assertEquals(2, invoices.count().intValue());
    assertNotEquals(null, invoices.getData());
    List<Invoice> invoicelist = invoices.getData();
    assertEquals(1, invoicelist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllInvoicesWithInvalidFilters() throws PayabbhiException {

    Invoice.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveInvoiceDetails() throws PayabbhiException {
    Invoice invoice = Invoice.retrieve("invc_v9YicJdb67siaXue");
    assertEquals("invc_v9YicJdb67siaXue", invoice.get("id"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", invoice.get("customer_id"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveInvoiceWithInvalidID() throws PayabbhiException {
    Invoice.retrieve("invc_invalid");
  }

  @Test
  public void testCreateNewInvoice() throws PayabbhiException {
    Map<String, Object> options = new HashMap<>();
    options.put("customer_id", "cust_J5fF1cj1KfSuI63S");
    options.put("due_date", 1544899262);
    options.put("currency", "INR");
    options.put("invoice_no", "INV_68934109");
    Map<String, Object> item1 = new HashMap<>();
    item1.put("name", "Amazom Prime Videos");
    item1.put("amount", 10000);
    item1.put("currency", "INR");
    List<Object> items = new ArrayList<>();
    items.add(item1);
    options.put("line_items", items);
    Invoice invoice = Invoice.create(options);
    assertEquals("invt_v1uXGmhIUMylFQPS", invoice.get("id"));
    assertEquals("cust_2WmsQoSRZMWWkcZg", invoice.get("customer_id"));
    assertEquals((Integer) 1549176945, invoice.get("due_date"));
    assertEquals("INR", invoice.get("currency"));
    assertEquals("123123123123", invoice.get("invoice_no"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNoInvoiceWhenParamsLessThanMin() throws PayabbhiException {
    Map<String, Object> options = new HashMap<>();
    options.put("customer_id", "cust_J5fF1cj1KfSuI63S");
    options.put("due_date", 1544899262);
    Invoice.create(options);
  }

  @Test
  public void testVoidInvoice() throws PayabbhiException {
    Invoice invoice = Invoice.markVoid("invc_v9YicJdb67siaXue");
    assertEquals("cust_VD9uYO8uc29b4hY8", invoice.get("customer_id"));
    assertEquals("123123123123", invoice.get("invoice_no"));
    assertEquals("void", invoice.get("status"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCancelInvoiceWithInvalidID() throws PayabbhiException {
    Invoice.markVoid("invc_invalid");
  }

  @Test
  public void testRetrievePaymentsOfInvoice() throws PayabbhiException {
    PayabbhiCollection<Payment> payments = Invoice.payments("invt_srxOZZk6dIgWTVls");
    assertEquals(1, payments.count().intValue());
    assertNotEquals(null, payments.getData());
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrievePaymentsOfInvoiceWithInvalidInvoiceID() throws PayabbhiException {
    Invoice.payments("invc_invalid");
  }

  @Test
  public void testRetrieveLineItemsOfInvoice() throws PayabbhiException {
    PayabbhiCollection<InvoiceItem> lineitems = Invoice.lineItems("item_FL3nHHB7i7Fpcj1J");
    assertEquals(1, lineitems.count().intValue());
    assertNotEquals(null, lineitems.getData());
    List<InvoiceItem> invoiceItemList = lineitems.getData();
    assertEquals(1, invoiceItemList.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveLineItemsOfInvoiceWithInvalidInvoiceID() throws PayabbhiException {
    Invoice.lineItems("invc_invalid");
  }
}
