package framework;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import logger.Log;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * All the validation methods and method to take screenshot 
 * are defined in this class.
 */
public class Assertions {
	public boolean testCaseStatus = true;
	private WebDriver driver;
	private File file;
	private String testScreenshotDir;
	boolean testStatus;

	public Assertions(WebDriver driver) {
		file = new File("");
		testScreenshotDir = file.getAbsoluteFile()
				+ "//src//test//java//outputFiles//";
		this.driver = driver;
		
	}

	/**
	 * method to take screenshot
	 * @return path where screenshot has been saved
	 */
	public String screenShot() {
		String screenshotPath = "screenshot" + new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss")
		.format(new GregorianCalendar().getTime())
		+ ".png";

		System.out.println(screenshotPath);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File( testScreenshotDir + screenshotPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			screenshotPath = "";
		}
		return screenshotPath;
	}
	
	/**
	 * overloaded method to take screenshot with desired screenshot name passed
	 * @param message string passed to save as name of a screenshot
	 */
	public void screenShot(String message) {
		String screenshotPath = message+ "screenshot" + new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss")
		.format(new GregorianCalendar().getTime())+
		".png";

		System.out.println(screenshotPath);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File( testScreenshotDir + screenshotPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			screenshotPath = "";
		}
	}

	
	/**
	 * method to verify the actual value with expected value
	 * @param actual actual text displayed
	 * @param expected expected text to be displayed
	 * @param message message should be displayed on failure of assertion
	 */
	public boolean verifyEquals(Object actual, Object expected, String message, boolean screenshotOnFailure, boolean exitOnFailure) {
		testStatus=true;
		Reporter.log("<br>");
		try {
			Assert.assertEquals(actual, expected, message);
			Reporter.log("<Font Color=#008000> PASS </Font>" + message);

		} catch (AssertionError e) {
		
			testStatus = false; 
			
			if (screenshotOnFailure) {
				
				Reporter.log( "<Font Color=red> FAIL </Font> " + message + " Actual: "+ actual + " Expected: " + expected
						+ " Please check the screenshot " +"<a href='" + screenShot()
							+ "'> <Font Color=red> here </Font> </a>" );

			} 

			if (exitOnFailure) {
				Reporter.log("<br>");
				Reporter.log("Exiting this function as exitOnFail flag is set to True. Will move to next test.");
				throw e;
			}
			
			Reporter.log( "<Font Color=red> FAIL </Font> " + message + " Actual: "+ actual + " Expected: " + expected);

		}
		return testStatus;
	}

	
	/**
	 * method to verify if the condition is true
	 * @param condition statement to verify
	 * @param message message should be displayed on failure of assertion
	 * @param screenshotOnFailure true if screenshot has to be taken in case of failure
	 * @param exitOnFailure true if execution to be stopped in case of failure 
	 * @return true if assertion passes, false if fails
	 */
	public boolean verifyTrue(boolean condition, String message,
			boolean screenshotOnFailure, boolean exitOnFailure) {

		Reporter.log("<br>");

		try {
		
			Assert.assertTrue(condition, message);
			Reporter.log("<Font Color=#008000> PASS </Font>" + message);
		
		} catch (AssertionError e) {
			Log.info(message);
			this.testCaseStatus = false;
			
			
			if (screenshotOnFailure) {
				Reporter.log( "<Font Color=red> FAIL </Font> " + message + " Actual: FALSE Expected: TRUE."
						+ " Please check the screenshot " +"<a href='" + screenShot()
							+ "'> <Font Color=red> here </Font> </a>" );

			} else {

				Reporter.log("<Font Color=red> FAIL </Font> " + message);

			}

			if (exitOnFailure) {
				Reporter.log("<br>");

				Reporter.log("Exiting this function as exitOnFail flag is set to True.");

				throw e;

			}

		}

		return this.testCaseStatus;
	}
	

}


