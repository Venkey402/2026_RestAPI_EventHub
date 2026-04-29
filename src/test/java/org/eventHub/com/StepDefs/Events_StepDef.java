package org.eventHub.com.StepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.eventHub.com.TestContext.TestContext;
import org.testng.Assert;
import static io.restassured.RestAssured.given;

public class Events_StepDef extends BaseClass{
    TestContext testContext;

    public Events_StepDef(TestContext testContext)
    {
        this.testContext=testContext;
    }

    @And("get the list of events")
    public void getTheListOfEvents() {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+testContext.token)
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
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+testContext.token).body(testContext.getEventDetails())
                .when().post("/events")
                .then().log().all().statusCode(201).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        testContext.eventId = jsonPath.getInt("data.id");
        Assert.assertEquals(jsonPath.getString("message"),"Event created successfully");
        System.out.println(testContext.eventId);
    }

    @And("get the event details")
    public void getTheEventDetails()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+testContext.token)
                .when().get("/events/"+ testContext.eventId)
                .then().log().all().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        System.out.println(response);
    }

    @Then("update the price of the event")
    public void updateThePriceOfTheEvent()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+testContext.token).body(testContext.udpatedEventDetails())
                .when().put("/events/"+ testContext.eventId)
                .then().log().all().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        System.out.println(response);
    }

    @And("delete an event")
    public void deleteAnEvent()
    {
        String response =  given().spec(testContext.requestSpec).header("Authorization","Bearer "+testContext.token)
                .when().delete("/events/"+ testContext.eventId)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
