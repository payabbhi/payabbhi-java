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
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/orders/order_aCsXtMDdTafnDbHd", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/orders/order_aCsXtMDdTafnDbHd.json", 200));

		// Create New Order
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/orders",
				"{\"amount\":10000,\"merchant_order_id\":\"merchant_100\",\"currency\":\"INR\"}"))
						.thenReturn(respondWith("/api/v1/orders/create.json", 200));

		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/orders",
				"{\"merchant_order_id\":\"merchant_100\",\"amount\":10000,\"currency\":\"INR\"}"))
						.thenReturn(respondWith("/api/v1/orders/create.json", 200));

		// Create Incorrect Order (Invalid Currency)
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/orders",
				"{\"amount\":10000,\"merchant_order_id\":\"merchant_101\",\"currency\":\"RUP\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));
		// 1.8+
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/orders",
				"{\"merchant_order_id\":\"merchant_101\",\"amount\":10000,\"currency\":\"RUP\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Get All Payments Associated with the Order
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/orders/order_aCsXtMDdTafnDbHd/payments", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/orders/payments.json", 200));

		// Get All Payments for INCORRECT ORDER
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/orders/hypothetical_order/payments", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Get All Payments
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/payments", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/payments/all.json", 200));

		// Get All Payments Done after a timestamp
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/payments?from=1531208000", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/payments/filteredall.json", 200));

		// Retrieve Particular Payment
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/payments/pay_C5OcIOxiCrZXAcHG.json", 200));

		// Capture a Payment
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG/capture", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/payments/capture.json", 200));

		// Get All Refunds Associated with Payment
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/payments/pay_R6mPqlzzukJTgWbS/refunds", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/payments/refund_pay_R6mPqlzzukJTgWbS.json", 200));

		// Get All Transfer Associated with Payment
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/payments/pay_R6mPqlzzukJTgWbS/transfers", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/payments/transfers.json", 200));

		// Get All Refunds
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/refunds", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/refunds/all.json", 200));

		// Get only 2 refunds
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/refunds?count=2", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/refunds/filteredall.json", 200));

		// Retrieve Particular Refund
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/refunds/rfnd_nMegjyzBnXfUZTb3", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/refunds/refund_rfnd_nMegjyzBnXfUZTb3.json", 200));

		// Create Partial Refund for payment
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG/refunds",
				"{\"amount\":100}")).thenReturn(respondWith("/api/v1/refunds/create.json", 200));

		// Create Complete Refund for payment
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/payments/pay_reSjReWuDnOVz2zr/refunds", "{}"))
				.thenReturn(respondWith("/api/v1/refunds/completerefund.json", 200));

		// Create Invalid Refund for Payment
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/payments/pay_C5OcIOxiCrZXAcHG/refunds",
				"{\"amount\":10000}")).thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Get all customers
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/customers", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/customers/all.json", 200));

		// Get customers with count =1
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/customers?count=1", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/customers/filteredall.json", 200));

		// Get all customers will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/customers?env=live", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 200));

		// Retrieve a customer
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/customers/cust_NDgjdWVgjKyb0qac", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/customers/cust_NDgjdWVgjKyb0qac.json", 200));

		// Retrieve a customer wiht invalid ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/customers/cust_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a Customer 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/customers",
				"{\"email\":\"abc@bca.com\",\"contact_no\":\"9433894351\"}"))
						.thenReturn(respondWith("/api/v1/customers/create.json", 200));

		// Create a Customer
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/customers",
				"{\"contact_no\":\"9433894351\",\"email\":\"abc@bca.com\"}"))
						.thenReturn(respondWith("/api/v1/customers/create.json", 200));

		// Create a customer with invalid contact no
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/customers",
				"{\"contact_no\":\"9876543\",\"email\":\"abc@bca.com\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a customer with invalid contact no
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/customers",
				"{\"email\":\"abc@bca.com\",\"contact_no\":\"9876543\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a customer without emailid
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/customers", "{\"contact_no\":\"9876543210\"}"))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Update Customer with mandatory parameters
		when(mf.fetch(Method.PUT, "https://payabbhi.com/api/v1/customers/cust_NDgjdWVgjKyb0qac",
				"{\"contact_no\":\"9433894000\",\"email\":\"change@mail.com\"}"))
						.thenReturn(respondWith("/api/v1/customers/updated.json", 200));

		// Update Customer with mandatory parameters 1.7
		when(mf.fetch(Method.PUT, "https://payabbhi.com/api/v1/customers/cust_NDgjdWVgjKyb0qac",
				"{\"email\":\"change@mail.com\",\"contact_no\":\"9433894000\"}"))
						.thenReturn(respondWith("/api/v1/customers/updated.json", 200));

		// Update Customer without one mandatory parameters
		when(mf.fetch(Method.PUT, "https://payabbhi.com/api/v1/customers/cust_NDgjdWVgjKyb0qac",
				"{\"email\":\"change@mail.com\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Get all products
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/products", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/products/all.json", 200));

		// Get products with count =1
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/products?count=1", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/products/filteredall.json", 200));

		// Get all products will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/products?count=", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Retrieve a product with invalid ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/products/prod_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a product without mandatory params
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/products", "{\"unit_label\":\"MB\"}"))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Retrieve a product
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/products/prod_v0RYyTj4qEj56c12", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/products/prod_v0RYyTj4qEj56c12.json", 200));

		// Create a product
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/products",
				"{\"name\":\"Books\",\"type\":\"service\",\"unit_label\":\"MB\"}"))
						.thenReturn(respondWith("/api/v1/products/create.json", 200));

		// Create a product 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/products",
				"{\"type\":\"service\",\"unit_label\":\"MB\",\"name\":\"Books\"}"))
						.thenReturn(respondWith("/api/v1/products/create.json", 200));

		// Get all Plans
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/plans", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/plans/all.json", 200));

		// Get plans with product_id = prod_wJ6DyX5Bgg2LqAqt
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/plans?product_id=prod_wJ6DyX5Bgg2LqAqt", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/plans/filteredall.json", 200));

		// Get plans with count = 2
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/plans?count=2", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/plans/filteredall.json", 200));

		// Retrieve a plan withinvalid ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/plans/plan_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a plan with less params
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/plans",
				"{\"amount\":100,\"currency\":\"INR\",\"product_id\":\"prod_wJ6DyX5Bgg2LqAqt\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a plan with less params 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/plans",
				"{\"product_id\":\"prod_wJ6DyX5Bgg2LqAqt\",\"amount\":100,\"currency\":\"INR\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Get all plans will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/plans?count=", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Retrieve a plan
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/plans/plan_P7wNUwTdGC2u2n2I", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/plans/plan_P7wNUwTdGC2u2n2I.json", 200));

		// Create a plan
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/plans",
				"{\"amount\":100,\"currency\":\"INR\",\"interval\":\"month(s)\","
						+ "\"product_id\":\"prod_wJ6DyX5Bgg2LqAqt\",\"frequency\":2}"))
								.thenReturn(respondWith("/api/v1/plans/create.json", 200));

		// Create a plan 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/plans",
				"{\"amount\":100,\"frequency\":2,\"product_id\":\"prod_wJ6DyX5Bgg2LqAqt\","
						+ "\"interval\":\"month(s)\",\"currency\":\"INR\"}"))
								.thenReturn(respondWith("/api/v1/plans/create.json", 200));

		// Get all Subscriptions
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/subscriptions", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/subscriptions/all.json", 200));

		// Get subscriptions with plan_id = plan_tuOWN0Sc0uMB4s8E
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/subscriptions?plan_id=plan_tuOWN0Sc0uMB4s8E",
				EMPTY_BODY)).thenReturn(respondWith("/api/v1/subscriptions/filteredall.json", 200));

		// Get subscriptions with count = 1
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/subscriptions?count=1", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/subscriptions/filteredall.json", 200));

		// Get all subscriptions will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/subscriptions?count=", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 200));

		// Retrieve a subscription
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/subscriptions/sub_luQ4QIXzaEIN0g5D", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/subscriptions/sub_luQ4QIXzaEIN0g5D.json", 200));

		// Retrieve a subscription with invalid ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/subscriptions/sub_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a subscription with less params
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/subscriptions",
				"{\"customer_id\":\"cust_2WmsQoSRZMWWkcZg\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Cancel a subscription with invalid option params
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/subscriptions/sub_luQ4QIXzaEIN0g5D/cancel",
				"{\"cycle_end\":true}")).thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Cancel a subscription with invalid ID
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/subscriptions/sub_invalid/cancel", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a subscription 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/subscriptions",
				"{\"billing_cycle_count\":5,\"customer_id\":\"cust_2WmsQoSRZMWWkcZg\","
						+ "\"plan_id\":\"plan_tuOWN0Sc0uMB4s8E\"}"))
								.thenReturn(respondWith("/api/v1/subscriptions/create.json", 200));

		// Create a subscription
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/subscriptions",
				"{\"customer_id\":\"cust_2WmsQoSRZMWWkcZg\",\"billing_cycle_count\":5,"
						+ "\"plan_id\":\"plan_tuOWN0Sc0uMB4s8E\"}"))
								.thenReturn(respondWith("/api/v1/subscriptions/create.json", 200));

		// Cancel a subscription with option
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/subscriptions/sub_luQ4QIXzaEIN0g5D/cancel",
				"{\"at_billing_cycle_end\":true}"))
						.thenReturn(respondWith("/api/v1/subscriptions/filtercancel.json", 200));

		// Cancel a subscription
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/subscriptions/sub_luQ4QIXzaEIN0g5D/cancel", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/subscriptions/cancelled.json", 200));

		// Get all InvoiceItems
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoiceitems", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoiceitems/all.json", 200));

		// Get invoiceitems with count = 1
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoiceitems?count=1", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoiceitems/filteredall.json", 200));

		// Get all invoiceitems will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoiceitems?count=", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Retrieve a invoiceitem
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoiceitems/item_zvenYE0Tk8qTUaER", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoiceitems/item_zvenYE0Tk8qTUaER.json", 200));

		// Retrieve a invoiceitem with invalid ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoiceitems/item_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create an InvoiceItem with less params
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoiceitems",
				"{\"name\":\"Line Item\",\"amount\":100}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create an InvoiceItem with less params 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoiceitems",
				"{\"amount\":100,\"name\":\"Line Item\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// delete an Invoice Item with invalid ID
		when(mf.fetch(Method.DELETE, "https://payabbhi.com/api/v1/invoiceitems/item_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a invoiceitem
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoiceitems",
				"{\"name\":\"Line Item\",\"amount\":100,\"currency\":\"INR\","
						+ "\"customer_id\":\"cust_2WmsQoSRZMWWkcZg\"}"))
								.thenReturn(respondWith("/api/v1/invoiceitems/create.json", 200));

		// Create a invoiceitem 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoiceitems",
				"{\"amount\":100,\"customer_id\":\"cust_2WmsQoSRZMWWkcZg\","
						+ "\"currency\":\"INR\",\"name\":\"Line Item\"}"))
								.thenReturn(respondWith("/api/v1/invoiceitems/create.json", 200));

		// Delete a invoiceitem
		when(mf.fetch(Method.DELETE, "https://payabbhi.com/api/v1/invoiceitems/item_zvenYE0Tk8qTUaER", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoiceitems/delete.json", 200));

		// Get invoices with customer_id = cust_J5fF1cj1KfSuI63S
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices?customer_id=cust_J5fF1cj1KfSuI63S", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/filteredall.json", 200));

		// Retrieve all invoices of an invoiceItem
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoiceitems/item_KI75WbBlizfzLZui/invoices", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/all.json", 200));

		// Retrieve all invoices of an invoiceItem with invalid item ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoiceitems/item_invalid/invoices", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Get all invoice
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/all.json", 200));

		// Get invoices with count = 1
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices?count=1", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/filteredall.json", 200));

		// Get all invoices will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices?count=", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 200));

		// Retrieve a invoice
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices/invc_v9YicJdb67siaXue", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/invc_v9YicJdb67siaXue.json", 200));

		// Retrieve a invocice with invalid ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices/invc_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a invoice
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoices",
				"{\"due_date\":1544899262,\"currency\":\"INR\","
						+ "\"line_items\":[{\"name\":\"Amazom Prime Videos\",\"amount\":10000,"
						+ "\"currency\":\"INR\"}],\"customer_id\":\"cust_J5fF1cj1KfSuI63S\","
						+ "\"invoice_no\":\"INV_68934109\"}"))
								.thenReturn(respondWith("/api/v1/invoices/create.json", 200));

		// Create a invoice 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoices",
				"{\"due_date\":1544899262,\"line_items\":[{\"amount\":10000,"
						+ "\"currency\":\"INR\",\"name\":\"Amazom Prime Videos\"}],"
						+ "\"invoice_no\":\"INV_68934109\","
						+ "\"customer_id\":\"cust_J5fF1cj1KfSuI63S\",\"currency\":\"INR\"}"))
								.thenReturn(respondWith("/api/v1/invoices/create.json", 200));

		// Create a invoice with line item id
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoices",
				"{\"due_date\":1544899262,\"currency\":\"INR\","
						+ "\"line_items\":[{\"id\":\"item_zHPYd3Ro7ImGxUHB\"}],"
						+ "\"customer_id\":\"cust_J5fF1cj1KfSuI63S\",\"invoice_no\":\"INV_68934109\"}"))
								.thenReturn(respondWith("/api/v1/invoices/create.json", 200));

		// Create no invoice when parameters are less then min mandatory params required
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoices",
				"{\"due_date\":1544899262,\"customer_id\":\"cust_J5fF1cj1KfSuI63S\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create no invoice when parameters are less then min mandatory params required
		// 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoices",
				"{\"customer_id\":\"cust_J5fF1cj1KfSuI63S\",\"due_date\":1544899262}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Void an invoice with invalid ID
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoices/invc_invalid/void", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Retrieve all payment of an invoice with invalid invoice ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices/invc_invalid/payments", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Retrieve all lineItems of an invoice with invalid invoice ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices/invc_invalid/line_items", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Void an inovice
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/invoices/invc_v9YicJdb67siaXue/void", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/void.json", 200));

		// Retrieve all payment of an invoice
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices/invc_123456700test002/payments", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/payments.json", 200));

		// Retrieve all line_items of an invoice
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/invoices/invc_123456700test002/line_items", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/invoices/items.json", 200));

		// Get all transfers
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/transfers", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/transfers/all.json", 200));

		// Get transfers with count =1
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/transfers?count=2", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/transfers/filteredall.json", 200));

		// Get all transfers will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/transfers?count=", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 200));

		// Retrieve a transfer
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/transfers/trans_ucwszWrXUZJGDgMX", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/transfers/trans_ucwszWrXUZJGDgMX.json", 200));

		// Retrieve a transfer with invalid ID
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/transfers/trans_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a transfer with less parameter
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/payments/pay_W2FmbqANt09epUOz/transfers",
				"{\"recipient_id\":\"recp_Y2ojRlJVqRMhB0Ay\"}"))
						.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Create a transfer
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/payments/pay_W2FmbqANt09epUOz/transfers",
				"{\"amount\":50,\"currency\":\"INR\","
						+ "\"recipient_id\":\"recp_Y2ojRlJVqRMhB0Ay\"}"))
								.thenReturn(respondWith("/api/v1/transfers/create.json", 200));

		// Create a transfer 1.7
		when(mf.fetch(Method.POST, "https://payabbhi.com/api/v1/payments/pay_W2FmbqANt09epUOz/transfers",
				"{\"recipient_id\":\"recp_Y2ojRlJVqRMhB0Ay\",\"amount\":50,"
						+ "\"currency\":\"INR\"}"))
								.thenReturn(respondWith("/api/v1/transfers/create.json", 200));

		// Get all events
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/events", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/events/all.json", 200));

		// Get events with type =payment.refunded
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/events?type=payment.refunded", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/events/filteredall.json", 200));

		// Get all events will throw error because of invalid params
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/events?count=", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		// Retrieve a event
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/events/evt_gCmIpp76zgZynfEO", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/events/evt_gCmIpp76zgZynfEO.json", 200));

		// Retrieve a event with invalid id
		when(mf.fetch(Method.GET, "https://payabbhi.com/api/v1/events/evt_invalid", EMPTY_BODY))
				.thenReturn(respondWith("/api/v1/exceptions/invalidrequesterror.json", 400));

		APIResource.setFetcher(mf);
	}
}
