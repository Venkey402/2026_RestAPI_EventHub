package org.eventHub.com.StepDefs;

import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.eventHub.com.POJOs.UserDetails;
import org.eventHub.com.TestContext.TestContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class HealthCheck_StepDef extends BaseClass{
    RequestSpecification requestSpec;
    public String token;
    public String userid;
    UserDetails userDetails;
    TestContext testContext;

    public HealthCheck_StepDef(TestContext testContext)
    {
        this.testContext=testContext;
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
