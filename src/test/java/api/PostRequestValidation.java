package api;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

public class PostRequestValidation {
	
	@Test
	public void ResourceCreationwithValidData() {

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "morpheus"); // Body
		requestParams.put("job", "leader");
		request.body(requestParams.toJSONString());
		Response response = request.post("/users");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 201 Created" /* expected value */,
				"Correct status line didn't returned");
		Assert.assertTrue(response.getBody().asString().contains("id"));
		Assert.assertTrue(response.getBody().asString().contains("createdAt"));
		Reporter.log(response.asString());

	}

	@Test
	public void VerifyResourceCreationResponse() {

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "morpheus"); // Body
		requestParams.put("job", "leader");

		request.body(requestParams.toJSONString());
		Response response = request.post("/users");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 201 Created" /* expected value */,
				"Correct status line didn't returned");

		Assert.assertTrue(response.getBody().asString().contains("id"));
		Assert.assertTrue(response.getBody().asString().contains("createdAt"));

		//String id = response.jsonPath().getString("id");
		//String createdAt = response.jsonPath().getString("createdAt");

		Reporter.log(response.asString());

	}

	@Test
	public void ResourceCreationwithInvalidData() {

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "morpheus"); // Body
		requestParams.put("job", "leader");

		request.body(requestParams.toJSONString());
		Response response = request.post("/users");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 201 Created" /* expected value */,
				"Correct status line didn't returned");
		Assert.assertTrue(response.getBody().asString().contains("id"));
		Assert.assertTrue(response.getBody().asString().contains("createdAt"));

		Reporter.log(response.asString());

	}

	@Test
	public void ResourceCreationWithNullData() {

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		// requestParams.put(null, null);
		requestParams.put("", ""); // unable to set null in JSONObject
		request.body(requestParams.toJSONString());
		Response response = request.post("/users");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 201 Created" /* expected value */,
				"Correct status line didn't returned");
		Assert.assertTrue(response.getBody().asString().contains("id"));
		Assert.assertTrue(response.getBody().asString().contains("createdAt"));

		Reporter.log(response.asString());

	}

	@Test
	public void ResourceCreationWithNoBody() {

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		request.body(requestParams.toJSONString());
		Response response = request.post("/users");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 201 Created" /* expected value */,
				"Correct status line didn't returned");

		Reporter.log(response.asString());

	}

	public void ResourceCreationWithInCorrectURL() {

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		request.body(requestParams.toJSONString());
		Response response = request.post("/test");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 201 Created" /* expected value */,
				"Correct status line didn't returned");

		Assert.assertTrue(response.getBody().asString().contains("id"));
		Assert.assertTrue(response.getBody().asString().contains("createdAt"));
		Reporter.log(response.asString());

	}

}
