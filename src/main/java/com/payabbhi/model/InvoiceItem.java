package com.payabbhi.model;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import java.util.Map;
import org.json.JSONObject;

public class InvoiceItem extends APIResource {
  public InvoiceItem(JSONObject jsonObject) {
    super(jsonObject);
  }

  /**
   * Returns a list of invoiceitems created previously.
   *
   * @return a collection of invoiceitem objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<InvoiceItem> all() throws PayabbhiException {
    return requestCollection(Method.GET, urlFor(InvoiceItem.class), null, InvoiceItem.class);
  }

  /**
   * Returns a list of invoiceitems created previously with parameters.
   *
   * @param params a map of optional parameters to refine the search
   * @return a collection of invoiceitem objects
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static PayabbhiCollection<InvoiceItem> all(Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET, withParams(urlFor(InvoiceItem.class), params), null, InvoiceItem.class);
  }

  /**
   * Returns an invoiceitem object matching the given invoiceitem id.
   *
   * @param id an identifier of the invoiceitem object to retrieve
   * @return an invoiceitem object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static InvoiceItem retrieve(String id) throws PayabbhiException {
    return request(Method.GET, urlFor(InvoiceItem.class, id), null, InvoiceItem.class);
  }

  /**
   * Creates a new invoiceitem.
   *
   * @param params a map consisting of parameters used for creating an invoiceitem object:
   * @return the newly created invoiceitem object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static InvoiceItem create(Map<String, Object> params) throws PayabbhiException {
    return request(Method.POST, urlFor(InvoiceItem.class), params, InvoiceItem.class);
  }

  /**
   * Delete a new invoiceitem.
   *
   * @param id a identifier for deleteing an invoiceitem object:
   * @return the invoiceitem object
   * @throws PayabbhiException if there is a problem in performing the operation.
   */
  public static InvoiceItem delete(String id) throws PayabbhiException {
    return request(Method.DELETE, urlFor(InvoiceItem.class, id), null, InvoiceItem.class);
  }

  /**
   * Returns a list of all invoices where this invoiceitem is attached.
   *
   * @param id the identifier of the invoiceitem whose invoices are to be retrieved
   * @return a collection of invoice objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Invoice> invoices(String id) throws PayabbhiException {
    return invoices(id, null);
  }

  /**
   * Returns a list of all invoices where this invoiceitem is attached.
   *
   * @param id the identifier of the invoiceitem whose invoices are to be retrieved
   * @param params A map of optional parameters to refine the search
   * @return a collection of invoice objects
   * @throws PayabbhiException if there is a problem in performing the operation
   */
  public static PayabbhiCollection<Invoice> invoices(String id, Map<String, Object> params)
      throws PayabbhiException {
    return requestCollection(
        Method.GET,
        withParams(urlFor(InvoiceItem.class, id, "invoices"), params),
        null,
        Invoice.class);
  }
}
