package api;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.nullValue;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import static org.hamcrest.Matchers.equalTo;

public class GetTesting {
	Response response;

	/*
	 * No tests are performed on Authentication as there are no specifications
	 * mentioned
	 * 
	 */
	@Test
	public void verifyingStatusCode() {
		response = given().get("/users");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "in Correct Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 200 OK" /* expected value */,
				"Correct status line didn't returned");

		System.out.println("Response code - " + response.getStatusCode());

		response.then().assertThat().header("sessionId", nullValue()); // Session ID

		Assert.assertTrue(response.getBody().asString().contains("id"));
		Assert.assertTrue(response.getBody().asString().contains("email"));

		Reporter.log(response.asString()); // converting response into string

	}

	@Test
	public void verifyingWithIncorrectURL_DefaultResponse() {
		
		// https://reqres.in/api
		response = given().get("/test");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "inCorrect Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 200 OK" /* expected value */,
				"in Correct status line didn't returned");
		Reporter.log(response.asString());
	}

	@Test
	public void verifyingWithIncompleteURL() {

		response = given().get("");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 404, "inCorrect Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 404 Not Found" /* expected value */,
				"in Correct status line didn't returned");
		Reporter.log(response.asString());
	}

	/*
	 * @Test public void verifyingWithInValidURL() { response =
	 * given().get("(363%^$"); //!@&&$#gh# int statusCode =
	 * response.getStatusCode(); System.out.println(response.asString());
	 * Assert.assertEquals(statusCode, 400, "inCorrect Status Code returned");
	 * 
	 * String statusLine = response.getStatusLine(); Assert.assertEquals(statusLine
	 * actual value , "HTTP/1.1 400 Bad Request" expected value ,
	 * "in Correct status line didn't returned"); Reporter.log(response.asString());
	 * }
	 */

	@Test
	public void verifyingGetResponseWithValidURL() {
		
		response = given().get("/users");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "in Correct Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 200 OK" /* expected value */,
				"incorrect status line returned");

		System.out.println("Response code - " + response.getStatusCode());

		
		Assert.assertTrue(response.getBody().asString().contains("id"));
		Assert.assertTrue(response.getBody().asString().contains("email"));

		response.then().assertThat().body("data[0].id", equalTo(1));
		response.then().assertThat().body("data[0].email", equalTo("george.bluth@reqres.in"));
		response.then().assertThat().body("data[0].first_name", equalTo("George"));
		response.then().assertThat().body("data[0].last_name", equalTo("Bluth"));
		response.then().assertThat().body("data[0].avatar", equalTo("https://reqres.in/img/faces/1-image.jpg"));

		Reporter.log(response.asString());
	}

	@Test
	public void verifyingGetResponseWithInCorrectURL() {
		response = given().get("/3sdf");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "in Correct Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 200 OK" /* expected value */,
				"in Correct status line didn't returned");

		System.out.println("Response code - " + response.getStatusCode());

		response.then().assertThat().header("sessionId", nullValue());

		// System.out.println(response.body().data[0].id);
		
		// We are using index because there is multiple arrays of data in the bottom example there is only SINGLE data. 

		response.then().assertThat().body("data[0].id", equalTo(1));
		response.then().assertThat().body("data[0].name", equalTo("cerulean"));
		response.then().assertThat().body("data[0].year", equalTo(2000));
		response.then().assertThat().body("data[0].color", equalTo("#98B2D1"));
		response.then().assertThat().body("data[0].pantone_value", equalTo("15-4020"));

		Reporter.log(response.asString());
	}
	
	@Test
	public void verifyingGetResponseWithCorrectQueryParam() {

		response = given().queryParam("id", 3).get("/users"); //users?id=3
		// response = given().get("/users?id=3"); 
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "in Correct Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 200 OK" /* expected value */,
				"in Correct status line didn't returned");

		System.out.println("Response code - " + response.getStatusCode());

		response.then().assertThat().header("sessionId", nullValue());

		response.then().assertThat().body("data.id", equalTo(3));
		response.then().assertThat().body("data.email", equalTo("emma.wong@reqres.in"));
		response.then().assertThat().body("data.first_name", equalTo("Emma"));
		response.then().assertThat().body("data.last_name", equalTo("Wong"));
		response.then().assertThat().body("data.avatar", equalTo("https://reqres.in/img/faces/3-image.jpg"));
		response.then().assertThat().body("support.url", equalTo("https://reqres.in/#support-heading"));
		response.then().assertThat().body("data.text",
				equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));

		Reporter.log(response.asString());
	}

	@Test
	public void verifyingGetResponseWithInCorrectQueryParam_int() {
		response = given().get("/users?id=789789");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 404, "in Correct Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 404 Not Found" /* expected value */,
				"in Correct status line didn't returned");

		System.out.println("Response code - " + response.getStatusCode());

		Assert.assertTrue(response.asString().contains("{}"));

		Reporter.log(response.asString());
	}

	@Test
	public void verifyingGetResponseWithInCorrectQueryParam_String() {
		response = given().get("/users?id=Hello");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 404, "in Correct Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 404 Not Found" /* expected value */,
				"in Correct status line didn't returned");

		System.out.println("Response code - " + response.getStatusCode());

		Assert.assertTrue(response.asString().contains("{}"));

		Reporter.log(response.asString());
	}

	@Test
	public void verifyingGetResponseWithMultipleParameters() {
		response = given().get("/users?id=1&id=2");
		/*anil id = 1 anil93@gmail.com age =20
		ahsen id = 4 ahsen93@hotmail.co.uk age = 20*/
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 404, "in Correct Status Code returned");

		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 404 Not Found" /* expected value */,
				"in Correct status line didn't returned");

		System.out.println("Response code - " + response.getStatusCode());

		Assert.assertTrue(response.asString().contains("{}"));

		Reporter.log(response.asString());
	}

	@BeforeMethod
	public void beforeMethod() {
		RestAssured.baseURI = "https://reqres.in/api";

	}

}
