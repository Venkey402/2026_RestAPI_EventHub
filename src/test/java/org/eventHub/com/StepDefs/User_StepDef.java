package org.eventHub.com.StepDefs;

import static io.restassured.RestAssured.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.eventHub.com.POJOs.UserDetails;
import org.eventHub.com.TestContext.TestContext;
import org.eventHub.com.Utilities.JsonParser;
import org.testng.Assert;
import org.testng.annotations.Test;


public class User_StepDef extends BaseClass{
    RequestSpecification requestSpec;
    public String token;
    public String userid;
    UserDetails userDetails;
    TestContext testContext;

    public User_StepDef(TestContext testContext)
    {
        this.testContext=testContext;
    }

    @Given("user registers to event hub")
    public void user_registers_to_event_hub()
    {
        testContext.requestSpec = new RequestSpecBuilder().setBaseUri("https://api.eventhub.rahulshettyacademy.com/api").setContentType("application/json").build();
        BaseClass baseClass = new BaseClass();
        String response = given().spec(testContext.requestSpec).log().all().body(baseClass.createUserDetailsPojo())
                .when().post("/auth/register")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        userid=jsonParser.get(response,"user.id");
        token=jsonParser.get(response,"token");
        Assert.assertEquals("true",jsonParser.get(response,"success"));
    }

    @Then("login with user")
    public void login_with_user() {
        String response = given().spec(testContext.requestSpec).log().all().body(createUserDetailsPojo())
                .when().post("/auth/login")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        Assert.assertEquals("true",jsonParser.get(response,"success"));
    }

    @And("validate user token")
    public void validateUserToken()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/auth/me")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        Assert.assertEquals("true",jsonParser.get(response,"success"));
        Assert.assertEquals(userid,jsonParser.get(response,"user.userId"));
    }
}
