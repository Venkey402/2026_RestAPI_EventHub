package org.eventHub.com.StepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.eventHub.com.POJOs.UserDetails;
import org.eventHub.com.TestContext.TestContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class Bookings_StepDef extends BaseClass{
    RequestSpecification requestSpec;
    public String token;
    public String userid;
    UserDetails userDetails;
    TestContext testContext;

    public Bookings_StepDef(TestContext testContext)
    {
        this.testContext=testContext;
    }

    @Then("create a booking to an event")
    public void createABookingToAnEvent()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token).body(setBookingDetails())
                .when().post("/bookings")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        Assert.assertEquals(jsonPath.getString("message"),"Booking confirmed!");
        bookingId=jsonPath.getInt("data.id");
        System.out.println(response);
    }

    @And("get list of all bookings")
    public void getListOfAllBookings()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/bookings")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
    }

    @Then("get a single booking details")
    public void getASingleBookingDetails()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/bookings/"+bookingId)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
    }

    @And("delete a booking")
    public void deleteABooking()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().delete("/bookings/"+bookingId)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
    }
}
