package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class Invoice extends APIResource {
  public Invoice(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of invoices created previously.
   *
   * @return a collection of invoice objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Invoice> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(Invoice.class), null, Invoice.class);
  }

  /**
   * Returns a list of invoices created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of invoice objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<Invoice> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(Invoice.class), params), null, Invoice.class);
  }

  /**
   * Returns an invoice object matching the given invoice id.
   *
   * @param id an identifier of the invoice object to retrieve
   * @return an invoice object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Invoice retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(Invoice.class, id), null, Invoice.class);
  }

  /**
   * Creates a new invoice.
   *
   * @param params a map consisting of parameters used for creating an invoice object:
   * @return the newly created invoice object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Invoice create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(Invoice.class), params, Invoice.class);
  }

  /**
   * Returns an invoice object matching the given invoice id.
   *
   * @param id an identifier of the invoice object to cancel
   * @return an invoice object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static Invoice cancel(String id) throws PayabbhiException {
    return request(Method.POST, urlFor(Invoice.class, id, "cancel"), null, Invoice.class);
  }

  /**
   * Returns a list of all payments for a given invoice that have previously been created.
   *
   * @param id the identifier of the invoice whose payments are to be retrieved
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id) throws PayabbhiException {
    return payments(id, null);
  }

  /**
   * Returns a list of all payments for a given invoice that have previously been created.
   *
   * @param id the identifier of the invoice whose payments are to be retrieved
   * @param params A map of optional parameters to refine the search
   * @return a collection of payment objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Payment> payments(String id, Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET,
        withParams(urlFor(Invoice.class, id, Payment.class), params),
        null,
        Payment.class);
  }

  /**
   * Returns a list of all invoiceitems for a given invoice that have previously been created.
   *
   * @param id the identifier of the invoice whose invoiceitems are to be retrieved
   * @return a collection of invoiceitem objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<InvoiceItem> lineItems(String id) throws PayabbhiException {
    return lineItems(id, null);
  }

  /**
   * Returns a list of all invoiceitems for a given invoice that have previously been created.
   *
   * @param id the identifier of the invoice whose invoiceitems are to be retrieved
   * @param params A map of optional parameters to refine the search
   * @return a collection of invoiceitem objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<InvoiceItem> lineItems(String id, Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET,
        withParams(urlFor(Invoice.class, id, "line_items"), params),
        null,
        InvoiceItem.class);
  }
}
