package org.eventHub.com.TestRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "C:\\Users\\venke\\IdeaProjects\\Apr28_RestAssured_EventHub\\src\\test\\java\\org\\eventHub\\com\\Features",
        glue = {"org.eventHub.com.StepDefs"},
        plugin = {"pretty","html:/src/testOutput"})
public class TestRunner extends AbstractTestNGCucumberTests {
}
