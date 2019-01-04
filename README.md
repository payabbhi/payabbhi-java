# Payabbhi Java Library
Make sure you have signed up for your [Payabbhi Account](https://payabbhi.com/docs/account) and downloaded the [API keys](https://payabbhi.com/docs/account/#api-keys) from the [Portal](https://payabbhi.com/portal).


## Requirements
Java 1.7 or later.

## Installation

### Maven
Include this dependency in your Maven `pom.xml`:

```xml
<dependency>
  <groupId>com.payabbhi</groupId>
  <artifactId>payabbhi-java</artifactId>
  <version>1.0.2</version>
</dependency>
```

### Gradle

Include this dependency in your Gradle build file `build.gradle`:

```
compile "com.payabbhi:payabbhi-java:1.0.2"
```

### Manual Download

An alternative is to manually download the JAR for [Payabbhi Java Library from Maven repository](https://mvnrepository.com/artifact/com.payabbhi/payabbhi-java)

Payabbhi Java library has the following dependencies which should be included separately:
* [commons-codec](http://central.maven.org/maven2/commons-codec/commons-codec/1.11/commons-codec-1.11.jar)
* [json](http://central.maven.org/maven2/org/json/json/20180130/json-20180130.jar)
* [okHttp](http://central.maven.org/maven2/com/squareup/okhttp3/okhttp/3.10.0/okhttp-3.10.0.jar)
* [okio](http://central.maven.org/maven2/com/squareup/okio/okio/1.14.0/okio-1.14.0.jar)



## Documentation

Please refer to:
- [Java Library Docs](https://payabbhi.com/docs/api/?java)
- [Integration Guide](https://payabbhi.com/docs/integration)


## Usage

A typical usage of the Payabbhi Java Library is shown below:

```java
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.model.Order;

public class Example {

	public static void main(String[] args) {

		Payabbhi.accessId  = "<your-access-id>";
		Payabbhi.secretKey = "<your-secret-key>";

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("merchant_order_id", "0211");
			params.put("amount", 10000);
			params.put("currency", "INR");

			Order order = Order.create(params);

			System.out.println(order);

		} catch (PayabbhiException e) {
			e.printStackTrace();
		}
	}

}
```

For more examples see the [Java API documentation](https://payabbhi.com/docs/api/?java)

### Payment Signature Verification
Payabbhi Java library provides utility function for verifying the payment signature received in the payment callback.

```java
Map<String, String> params = new HashMap()
params.put("order_id", "<order-id>");
params.put("payment_id", "<payment-id>");
params.put("payment_signature", "<payment-signature>");
Payabbhi.verifyPaymentSignature(params)
```

### Webhook Signature Verification
Payabbhi Java library provides utility function for webhook signature verification.

```java
import com.payabbhi.Payabbhi;

// In this call default value of replayInterval is 300 seconds
Payabbhi.verifyWebhookSignature("<payload>", "<signature>", "<secret>");

Payabbhi.verifyWebhookSignature("<payload>", "<signature>", "<secret>", <replayInterval>);
```
