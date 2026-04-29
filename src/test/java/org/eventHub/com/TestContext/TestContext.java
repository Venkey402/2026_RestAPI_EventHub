package org.eventHub.com.TestContext;

import io.restassured.specification.RequestSpecification;
import org.eventHub.com.POJOs.UserDetails;

public class TestContext {

    public String email;
    public String pass;
    public String token;
    public String userid;
    public UserDetails userDetails;
    public RequestSpecification requestSpec;
    public TestContext()
    {

    }
}
