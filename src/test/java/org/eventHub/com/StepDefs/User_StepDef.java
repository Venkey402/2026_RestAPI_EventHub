package org.eventHub.com.StepDefs;

import static io.restassured.RestAssured.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import org.eventHub.com.TestContext.TestContext;
import org.eventHub.com.Utilities.JsonParser;
import org.testng.Assert;

public class User_StepDef extends BaseClass{
    TestContext testContext;
    public User_StepDef(TestContext testContext)
    {
        this.testContext=testContext;
    }

    @Given("user registers to event hub")
    public void user_registers_to_event_hub()
    {
        testContext.requestSpec = new RequestSpecBuilder().setBaseUri("https://api.eventhub.rahulshettyacademy.com/api").setContentType("application/json").build();
        String response = given().spec(testContext.requestSpec).log().all().body(testContext.createUserDetailsPojo())
                .when().post("/auth/register")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        testContext.userid=jsonParser.get(response,"user.id");
        testContext.token=jsonParser.get(response,"token");
        Assert.assertEquals("true",jsonParser.get(response,"success"));
    }

    @Then("login with user")
    public void login_with_user() {
        String response = given().spec(testContext.requestSpec).log().all().body(testContext.createUserDetailsPojo())
                .when().post("/auth/login")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        Assert.assertEquals("true",jsonParser.get(response,"success"));
    }

    @And("validate user token")
    public void validateUserToken()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+testContext.token)
                .when().get("/auth/me")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        Assert.assertEquals("true",jsonParser.get(response,"success"));
        Assert.assertEquals(testContext.userid,jsonParser.get(response,"user.userId"));
    }
}
