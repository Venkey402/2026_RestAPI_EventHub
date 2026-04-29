package org.eventHub.com.Utilities;

import io.restassured.path.json.JsonPath;

public class JsonParser {
    JsonPath jp;
    public String get(String response,String field)
    {
        jp = new JsonPath(response);
        return  jp.getString(field);
    }
}
