package framework;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import logger.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


/**
 * This class contains wedriver creation and quitting methods. These are required while
 * running each and every scenario. Methods are defined under @Before and @After hooks
 * to get initialized at start and end of the test.
 * @author shanky
 *
 */
public class CreateSession {

	/**
	 * ThreadLocal variable which contains the webdriver instance which is used to perform browser interactions with.
	 */
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>(); 

	/**
	 * method to create webdriver instance.
	 * @throws InterruptedException
	 */
	@Before
	public static void createDriver() {

		// browser name value passed from command line
		String browserName = System.getProperty("browser");

		// headless value passed from command line
		String headless =System.getProperty("headless");

		DesiredCapabilities capability = new DesiredCapabilities();

		// if browser name value is not passed from commandline then by default test would run on chrome
		if (browserName == null)
			browserName = "chrome";

		// initializing the browser if headless paramter sent as yes, initialize phantomjs
		if( headless != null && headless.equalsIgnoreCase("yes")){

			System.setProperty("phantomjs.binary.path", "libs//phantomjs");		
			String user_agent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
			DesiredCapabilities cap = DesiredCapabilities.phantomjs();
			cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", user_agent);
			cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "loadImages", true);
			cap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "javascriptEnabled", true);

			// Start phantomjs driver
			webDriver.set(new PhantomJSDriver(cap));


		}
		// if browser name passed as firefox
		else if(browserName.equalsIgnoreCase("Firefox")){
			WebDriverManager.firefoxdriver().setup();
			capability.setBrowserName("Firefox");
			webDriver.set(new FirefoxDriver());

		}
		// if browser name passed as chrome
		else if(browserName.equalsIgnoreCase("chrome")){
			String OS = System.getProperty("os.name");
			if(OS.contains("Windows"))
			{
				WebDriverManager.chromedriver().setup();
				capability.setBrowserName("Chrome");
				capability.setPlatform(Platform.WIN8_1);
				webDriver.set(new ChromeDriver());
			}
			else if(OS.contains("Linux"))
			{
				WebDriverManager.chromedriver().setup();
				capability.setBrowserName("Chrome");
				capability.setPlatform(Platform.LINUX);
				webDriver.set(new ChromeDriver());
			}
			else if(OS.contains("Mac"))
			{
				WebDriverManager.chromedriver().setup();
				capability.setBrowserName("Chrome");
				capability.setPlatform(Platform.MAC);
				webDriver.set(new ChromeDriver());
			}
		}
		getWebDriver().manage().window().maximize();

	}




	/**
	 * @return the webdriver for the current thread
	 */
	public static WebDriver getWebDriver() {
		System.out.println("WebDriver: " + webDriver.get());
		return webDriver.get();
	}

	/**
	 * method executes at the end of each scenario and takes screenshot in case of scenario failure.
	 * Also, quit the webdriver
	 * @param scenario to verify if scenarios has passed or failed
	 */
	@After
	public void teardown(Scenario scenario){

		// Here will compare if test is failing then only it will enter into if condition

		if(scenario.isFailed())
		{
			try 
			{
				// Create reference of TakesScreenshot
				TakesScreenshot ts=(TakesScreenshot) getWebDriver();

				// Call method to capture screenshot
				File source=ts.getScreenshotAs(OutputType.FILE);

				FileUtils.copyFile(source, new File(".//src//test//java//outputFiles//"+ "FailScreenshot" + 
						new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss")
						.format(new GregorianCalendar().getTime())
						+ ".png"));
				Log.info("Scenario failed and screenshot saved in outputFiles folder");
			} 
			catch (Exception e)
			{

				Log.info("Exception while taking screenshot "+e.getMessage());

			}
		}



		Log.info("Shutting down driver" + "\n" + "----------------------------------------------");
		System.out.println("\n");
		// quitting the webdriver
		getWebDriver().quit();
	}



}
