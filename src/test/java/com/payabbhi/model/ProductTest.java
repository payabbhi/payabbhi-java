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
public class ProductTest extends BaseTest {

  /** It setups the payabbhi object with accessID and secretKey. */
  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllProducts() throws PayabbhiException {
    PayabbhiCollection<Product> products = Product.all();
    assertEquals(2, products.count().intValue());
    assertNotEquals(null, products.getData());
  }

  @Test
  public void testGetAllProductsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Product> products =
        Product.all(
            new HashMap<String, Object>() {
              {
                put("count", 1);
              }
            });
    assertEquals(2, products.count().intValue());
    assertNotEquals(null, products.getData());
    List<Product> productlist = products.getData();
    assertEquals(1, productlist.size());
  }

  @Test(expected = PayabbhiException.class)
  public void testGetAllProductsWithInvalidFilters() throws PayabbhiException {

    Product.all(
        new HashMap<String, Object>() {
          {
            put("count", null);
          }
        });
  }

  @Test
  public void testRetrieveProductDetails() throws PayabbhiException {
    Product product = Product.retrieve("prod_v0RYyTj4qEj56c12");
    assertEquals("prod_v0RYyTj4qEj56c12", product.get("id"));
    assertEquals("Books", product.get("name"));
  }

  @Test(expected = PayabbhiException.class)
  public void testRetrieveProductWithInvalidID() throws PayabbhiException {
    Product.retrieve("prod_invalid");
  }

  @Test
  public void testCreateNewProduct() throws PayabbhiException {
    Product product =
        Product.create(
            new HashMap<String, Object>() {
              {
                put("name", "Books");
                put("type", "service");
                put("unit_label", "MB");
              }
            });
    assertEquals("prod_v0RYyTj4qEj56c12", product.get("id"));
    assertEquals("Books", product.get("name"));
    assertEquals("MB", product.get("unit_label"));
  }

  @Test(expected = PayabbhiException.class)
  public void testCreateNewProductWithoutMandatoryParam() throws PayabbhiException {
    Product.create(
        new HashMap<String, Object>() {
          {
            put("unit_label", "MB");
          }
        });
  }
}
