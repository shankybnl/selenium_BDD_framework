package logic;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@RunWith(Cucumber.class)
@CucumberOptions (
		monochrome = true,
		features = "src/test/java/features", 
		plugin = {"pretty", "html:target/cucumber-html-report"},
		tags = {"~@Ignore"}
		)
public class TestRunner extends AbstractTestNGCucumberTests {

}
