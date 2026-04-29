package org.eventHub.com.StepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.eventHub.com.POJOs.UserDetails;
import org.eventHub.com.TestContext.TestContext;
import org.eventHub.com.Utilities.JsonParser;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class Events_StepDef extends BaseClass{
    RequestSpecification requestSpec;
    public String token;
    public String userid;
    UserDetails userDetails;
    TestContext testContext;

    public Events_StepDef(TestContext testContext)
    {
        this.testContext=testContext;
    }

    @And("get the list of events")
    public void getTheListOfEvents() {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/events")
                .then().log().all().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);

        int count = jsonPath.getInt("data.size()");

        for(int i=0;i<count;i++)
        {
            String title = jsonPath.getString("data["+i+"].title");
            System.out.println(title);
        }
    }

    @Then("create an event")
    public void createAnEvent()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token).body(getEventDetails())
                .when().post("/events")
                .then().log().all().statusCode(201).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        eventId = jsonPath.getInt("data.id");
        Assert.assertEquals(jsonPath.getString("message"),"Event created successfully");
        System.out.println(eventId);
    }

    @And("get the event details")
    public void getTheEventDetails()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/events/"+eventId)
                .then().log().all().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        System.out.println(response);
    }

    @Then("update the price of the event")
    public void updateThePriceOfTheEvent()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token).body(udpatedEventDetails())
                .when().put("/events/"+eventId)
                .then().log().all().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        System.out.println(response);
    }

    @And("delete an event")
    public void deleteAnEvent()
    {
        String response =  given().spec(testContext.requestSpec).header("Authorization","Bearer "+token)
                .when().delete("/events/"+eventId)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
