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
public class CustomerTest extends BaseTest {

  /** It setup th payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllCustomers() throws PayabbhiException {
    PayabbhiCollection<Customer> customers = Customer.all();
    assertEquals(2, customers.count().intValue());
    assertNotEquals(null, customers.getData());
  }

  @Test
  public void testGetAllCustomersWithFilters() throws PayabbhiException {
    PayabbhiCollection<Customer> customers =
        Customer.all(
            new HashMap<String, Object>() {
              {
                put("count", 1);
              }
            });
    assertEquals(2, customers.count().intValue());
    assertNotEquals(null, customers.getData());
    List<Customer> customerlist = customers.getData();
    assertEquals(1, customerlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllCustomersWithInvalidFilters() throws PayabbhiException {

    Customer.all(
        new HashMap<String, Object>() {
          {
            put("env", "live");
          }
        });
  }

  @Test
  public void testRetrieveCustomerDetails() throws PayabbhiException {
    Customer customer = Customer.retrieve("cust_NDgjdWVgjKyb0qac");
    assertEquals("cust_NDgjdWVgjKyb0qac", customer.get("id"));
    assertEquals("9433894351", customer.get("contact_no"));
    assertEquals("abc@bca.com", customer.get("email"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveCustomerWithInvalidID() throws PayabbhiException {
    Customer.retrieve("cust_invalid");
  }

  @Test
  public void testCreateNewCustomer() throws PayabbhiException {
    Customer customer =
        Customer.create(
            new HashMap<String, Object>() {
              {
                put("email", "abc@bca.com");
                put("contact_no", "9433894351");
              }
            });
    assertEquals("cust_NDgjdWVgjKyb0qac", customer.get("id"));
    assertEquals("9433894351", customer.get("contact_no"));
    assertEquals("abc@bca.com", customer.get("email"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewCustomerWithInvalidContactNo() throws PayabbhiException {
    Customer customer =
        Customer.create(
            new HashMap<String, Object>() {
              {
                put("email", "abc@bca.com");
                put("contact_no", "9876543");
              }
            });
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewCustomerWithoutEmail() throws PayabbhiException {
    Customer customer =
        Customer.create(
            new HashMap<String, Object>() {
              {
                put("contact_no", "9876543210");
              }
            });
  }

  @Test
  public void testUpdateCustomer() throws PayabbhiException {
    Customer customer =
        Customer.edit(
            "cust_NDgjdWVgjKyb0qac",
            new HashMap<String, Object>() {
              {
                put("email", "change@mail.com");
                put("contact_no", "9433894000");
              }
            });
    assertEquals("cust_NDgjdWVgjKyb0qac", customer.get("id"));
    assertEquals("9433894000", customer.get("contact_no"));
    assertEquals("change@mail.com", customer.get("email"));
  }

  @Test(expected = PayabbhiException.class)
  public void testUpdateCustomerWithLessThanMinMandatoryParams() throws PayabbhiException {
    Customer customer =
        Customer.edit(
            "cust_NDgjdWVgjKyb0qac",
            new HashMap<String, Object>() {
              {
                put("email", "change@mail.com");
              }
            });
  }
}
