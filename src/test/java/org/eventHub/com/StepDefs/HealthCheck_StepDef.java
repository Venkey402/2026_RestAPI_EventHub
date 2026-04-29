package org.eventHub.com.StepDefs;

import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.eventHub.com.TestContext.TestContext;
import org.testng.Assert;
import static io.restassured.RestAssured.given;

public class HealthCheck_StepDef extends BaseClass{

    TestContext testContext;

    public HealthCheck_StepDef(TestContext testContext)
    {
        this.testContext=testContext;
    }

    @Then("validate health check is fine")
    public void validateHealthCheckIsFine()
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
