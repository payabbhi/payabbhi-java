package com.payabbhi.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource;
import com.payabbhi.net.APIResource.Method;
import com.payabbhi.net.Fetcher;
import com.payabbhi.net.LiveAPIResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseTest {
  static final String EMPTY_BODY = "{}";

  public BaseTest() {
    super();
  }

  protected String getResourceAsString(String path) throws IOException {

    InputStream resource = getClass().getResourceAsStream(path);
    ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
    byte[] buf = new byte[1024];

    for (int i = resource.read(buf); i > 0; i = resource.read(buf)) {
      os.write(buf, 0, i);
    }

    return os.toString("utf8");
  }

  protected LiveAPIResponse respondWith(String resourcePath, int status) throws IOException {
    LiveAPIResponse resp = new LiveAPIResponse(getResourceAsString(resourcePath), status);
    return resp;
  }

  protected String requestBody(String resourcePath) throws IOException {
    return getResourceAsString(resourcePath);
  }

  protected void mockFetcher() throws PayabbhiException, IOException {
    Fetcher mf = mock(Fetcher.class);

    // Get All Orders
    when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/orders", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/orders/all.json", 200));

    // Get only those Orders for which Payments are currently in authorized status.
    when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/orders?authorized=true", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/orders/filteredall.json", 200));

    // Get Order throw exception because of incorrect query parameters.
    when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/orders?count=", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

    // Retrieve Particular Order
    when(mf.fetch(
            Method.GET, "https://payabbhi.com/api/v1/orders/order_aCsXtMDdTafnDbHd", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/orders/order_aCsXtMDdTafnDbHd.json", 200));

    // Create New Order
    when(mf.fetch(
            Method.POST,
            "https://payabbhi.com/api/v1/orders",
            "{\"amount\":10000,\"merchant_order_id\":\"merchant_100\",\"currency\":\"INR\"}"))
        .thenReturn(respondWith("/api/v1/orders/create.json", 200));

    when(mf.fetch(
            Method.POST,
            "https://payabbhi.com/api/v1/orders",
            "{\"merchant_order_id\":\"merchant_100\",\"amount\":10000,\"currency\":\"INR\"}"))
        .thenReturn(respondWith("/api/v1/orders/create.json", 200));

    // Create Incorrect Order (Invalid Currency)
    when(mf.fetch(
            Method.POST,
            "https://payabbhi.com/api/v1/orders",
            "{\"amount\":10000,\"merchant_order_id\":\"merchant_101\",\"currency\":\"RUP\"}"))
        .thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));
    // 1.8+
    when(mf.fetch(
            Method.POST,
            "https://payabbhi.com/api/v1/orders",
            "{\"merchant_order_id\":\"merchant_101\",\"amount\":10000,\"currency\":\"RUP\"}"))
        .thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

    // Get All Payments Associated with the Order
    when(mf.fetch(
            Method.GET,
            "https://payabbhi.com/api/v1/orders/order_aCsXtMDdTafnDbHd/payments",
            EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/orders/payments.json", 200));

    // Get All Payments for INCORRECT ORDER
    when(mf.fetch(
            Method.GET,
            "https://payabbhi.com/api/v1/orders/hypothetical_order/payments",
            EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

    // Get All Payments
    when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/payments", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/payments/all.json", 200));

    // Get All Payments Done after a timestamp
    when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/payments?from=1531208000", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/payments/filteredall.json", 200));

    // Retrieve Particular Payment
    when(mf.fetch(
            Method.GET, "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/payments/pay_C5OcIOxiCrZXAcHG.json", 200));

    // Capture a Payment
    when(mf.fetch(
            Method.POST,
            "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG/capture",
            EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/payments/capture.json", 200));

    // Get All Refunds Associated with Payment
    when(mf.fetch(
            Method.GET,
            "https://payabbhi.com/api/v1/payments/pay_R6mPqlzzukJTgWbS/refunds",
            EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/payments/refund_pay_R6mPqlzzukJTgWbS.json", 200));

    // Get All Refunds
    when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/refunds", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/refunds/all.json", 200));

    // Get only 2 refunds
    when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/refunds?count=2", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/refunds/filteredall.json", 200));

    // Retrieve Particular Refund
    when(mf.fetch(
            Method.GET, "https://payabbhi.com/api/v1/refunds/rfnd_nMegjyzBnXfUZTb3", EMPTY_BODY))
        .thenReturn(respondWith("/api/v1/refunds/refund_rfnd_nMegjyzBnXfUZTb3.json", 200));

    // Create Partial Refund for payment
    when(mf.fetch(
            Method.POST,
            "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG/refunds",
            "{\"amount\":100}"))
        .thenReturn(respondWith("/api/v1/refunds/create.json", 200));

    // Create Complete Refund for payment
    when(mf.fetch(
            Method.POST, "https://payabbhi.com/api/v1/payments/pay_reSjReWuDnOVz2zr/refunds", "{}"))
        .thenReturn(respondWith("/api/v1/refunds/completerefund.json", 200));

    // Create Invalid Refund for Payment
    when(mf.fetch(
            Method.POST,
            "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG/refunds",
            "{\"amount\":10000}"))
        .thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

    APIResource.setFetcher(mf);
  }
}
