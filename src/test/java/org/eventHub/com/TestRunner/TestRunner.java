package org.eventHub.com.TestRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/Resources/Features",
        glue = {"org.eventHub.com.StepDefs"},
        plugin = {"pretty","html:target/test-results/cucumber-report.html"})
public class TestRunner extends AbstractTestNGCucumberTests {
}
