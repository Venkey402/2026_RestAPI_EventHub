package org.eventHub.com.StepDefs;

import static io.restassured.RestAssured.*;
import io.cucumber.java.en.Given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.eventHub.com.POJOs.UserDetails;
import org.eventHub.com.TestContext.TestContext;
import org.eventHub.com.Utilities.JsonParser;
import org.testng.Assert;
import org.testng.annotations.Test;


public class EventHub_APITests extends BaseClass{
    RequestSpecification requestSpec;
    public String token;
    public String userid;
    UserDetails userDetails;
    TestContext testContext;

    public EventHub_APITests(TestContext testContext)
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

    @Test(priority = 1)
    public void loginUser()
    {
        String response = given().spec(testContext.requestSpec).log().all().body(createUserDetailsPojo())
                .when().post("/auth/login")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        Assert.assertEquals("true",jsonParser.get(response,"success"));
    }

    @Test(dependsOnMethods = {"CreatesNewUser"},priority = 2)
    public void validateToken()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/auth/me")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonParser jsonParser = new JsonParser();
        Assert.assertEquals("true",jsonParser.get(response,"success"));
        Assert.assertEquals(userid,jsonParser.get(response,"user.userId"));
    }

    @Test(priority = 3)
    public void getListOfEvents()
    {
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

    @Test(priority = 4)
    public void createEvent()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token).body(getEventDetails())
                .when().post("/events")
                .then().log().all().statusCode(201).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        eventId = jsonPath.getInt("data.id");
        Assert.assertEquals(jsonPath.getString("message"),"Event created successfully");
        System.out.println(eventId);
    }

    @Test(priority = 5)
    public void getEvent()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/events/"+eventId)
                .then().log().all().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        System.out.println(response);
    }

    @Test(priority = 6)
    public void updatePriceOfEvent()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token).body(udpatedEventDetails())
                .when().put("/events/"+eventId)
                .then().log().all().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        System.out.println(response);
    }

    @Test(enabled = false)
    public void deleteEvent()
    {
        String response =  given().spec(testContext.requestSpec).header("Authorization","Bearer "+token)
                .when().delete("/events/"+eventId)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }

    @Test(priority = 7)
    public void createBooking()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token).body(setBookingDetails())
                .when().post("/bookings")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        Assert.assertEquals(jsonPath.getString("message"),"Booking confirmed!");
        bookingId=jsonPath.getInt("data.id");
        System.out.println(response);
    }

    @Test(priority = 8)
    public void listAllBookings()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/bookings")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
    }

    @Test(priority = 9)
    public void getABookingDetails()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().get("/bookings/"+bookingId)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
    }

    @Test(priority = 10)
    public void deleteABooking()
    {
        String response = given().spec(testContext.requestSpec).log().all().header("Authorization","Bearer "+token)
                .when().delete("/bookings/"+bookingId)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
    }
    @Test(priority = 11)
    public void getHealthCheck()
    {
        String response = given().spec(testContext.requestSpec).log().all()
                .when().get("/health")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        Assert.assertEquals(jsonPath.getString("status"),"ok");
        Assert.assertEquals(jsonPath.getString("dbStatus"),"connected");
        System.out.println(response);
    }

}
