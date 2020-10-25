package framework;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import logger.Log;


/**
 * This class contains all the methods required by selenium
 * to perform actions on webelement. It is a repository so 
 * that same code need not to be written again.
 * @author shanky
 *
 */
public class CommonMethods extends Assertions {

	WebDriver driver = null;
	public final int timeOut = 45;
	Properties configProp = new Properties();
	protected FileInputStream configFis;
	protected File file = new File("");

	public CommonMethods(WebDriver driver) throws IOException{
		super(driver);
		this.driver = driver;	
	}



	/**
	 * method to open specified url
	 * @param url to open
	 */
	//Step to navigate to specified URL
	public void get(String url){
		driver.get(url);
	}


	/**
	 * method to navigate to specified page
	 * @param url navigation url 
	 */
	public void navigate(String url){
		driver.navigate().to(url);
	}

	/**
	 * method to click on an element with action class
	 * @param element to be clicked
	 */
	public void clickOnElementUsingActions(By element){
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(element));
		actions.click().perform();
	}
	
	
	/**
	 * method to click on an element using javascript
	 * @param element to be clicked
	 */
	public void clickOnElementUsingJs(By element){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webElement = driver.findElement(element);
		js.executeScript("arguments[0].click();", webElement);
	}
	
	

	/**
	 * method to get int part from a string
	 * @param getInt string passed
	 * @return
	 */
	public int getIntValue(String getInt){
		Pattern intsOnly = Pattern.compile("\\d+");
		Matcher makeMatch = intsOnly.matcher(getInt);
		makeMatch.find();
		String inputInt = makeMatch.group();
		return Integer.parseInt(inputInt);
	}


	/**
	 * method to get title of current webpage
	 * @return String name of a webpage
	 */
	public String getTitle(){
		return driver.getTitle();
	}

	/**
	 * method to wait until page is loaded completely
	 * @param PageName String name of current webpage
	 */
	public void waitForPageToLoad(String PageName) {
		String pageLoadStatus;
		JavascriptExecutor js;

		do {
			js = (JavascriptExecutor) driver;
			pageLoadStatus = (String)js.executeScript("return document.readyState");
			Log.info(".");
		} while ( !pageLoadStatus.equals("complete") );
		Log.info(PageName + " page loaded successfully");
	}


	/**
	 *  method verify whether element is present on screen
	 * @param targetElement element to be present
	 * @return true if element is present else throws exception
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied, and the thread is interrupted, either before
	 *  or during the activity.
	 */
	public Boolean isElementPresent(By targetElement) throws InterruptedException{
		Boolean isPresent = driver.findElements(targetElement).size() > 0;
		return isPresent;
	}


	/**
	 *  method verify whether element is not present on screen
	 * @param targetElement element not to be present
	 * @return true if element is not present else throws exception
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied, and the thread is interrupted, either before
	 *  or during the activity.
	 */
	public Boolean isElementNotPresent(By targetElement) throws InterruptedException{
		Boolean isPresent = (driver.findElements(targetElement).size() == 0);
		return isPresent;
	}


	/**
	 *  method to wait for an element to be visible
	 * @param targetElement element to be visible
	 * @return true if element is visible else throws TimeoutException
	 */
	public boolean waitForVisibility(By targetElement) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOfElementLocated(targetElement));
			return true;
		}
		catch(TimeoutException e ){
			System.out.println("Element is not visible: " + targetElement );
			System.out.println();
			System.out.println(e.getMessage());
			throw new TimeoutException();

		}
	}

	/**
	 *  method to wait for an element to be clickable
	 * @param targetElement element to be clickable
	 * @return true if element is clickable else throws TimeoutException
	 */
	public boolean waitForElementToBeClickable(By targetElement) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(targetElement));
			return true;
		}
		catch(TimeoutException e ){
			System.out.println("Element is not clickable: " + targetElement );
			System.out.println();
			System.out.println(e.getMessage());
			throw new TimeoutException();

		}
	}


	/**
	 *  method to wait for an element until it is invisible
	 * @param targetElement element to be invisible
	 * @return true if element gets invisible else throws TimeoutException
	 */
	public boolean waitForInvisibility(By targetElement) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(targetElement));
			return true;
		}
		catch(TimeoutException e ){
			System.out.println("Element is still visible: " + targetElement );
			System.out.println();
			System.out.println(e.getMessage());
			throw new TimeoutException();

		}
	}


	/**
	 * method to find an element
	 * @param locator element to be found
	 * @return WebElement if found else throws NoSuchElementException
	 */
	public WebElement findElement(By locator){
		try {
			WebElement element = driver.findElement(locator);
			return element;
		}
		catch (NoSuchElementException e){
			Log.logError(this.getClass().getName(), "findElement", "Element not found " + locator);
			throw new NoSuchElementException(e.getMessage());
		}
	}

	/**
	 *  method to find all the elements of specific locator
	 * @param locator element to be found
	 * @return return the list of elements if found else throws NoSuchElementException
	 */
	public List<WebElement> findElements(By locator){
		try {
			List<WebElement> element = driver.findElements(locator);
			return element;
		}
		catch (NoSuchElementException e){
			Log.logError(this.getClass().getName(), "findElements", "element not found" + locator);
			throw new NoSuchElementException(e.getMessage());
		}
	}

	/**
	 * method to match value with list elements and click on it
	 * @param fetchedListElements List of fetched value
	 * @param valueToBeMatched value to be matched with list elements
	 */
	public void clickOnMatchingValue(List<WebElement> fetchedListElements, String valueToBeMatched){

		for (WebElement element : fetchedListElements) {
			if (element.getText().equalsIgnoreCase(valueToBeMatched)) {
				element.click();
				return;
			}
			//System.out.println(element.getText() );
		}
	}


	/**
	 * method to check value contained in list elements and click on it
	 * @param fetchedListElements List of fetched value
	 * @param valueToBeContained value to be contained in list elements
	 */
	public void clickOnContainingValue(List<WebElement> fetchedListElements, String valueToBeContained){

		for (WebElement element : fetchedListElements) {
			if (element.getText().toLowerCase().contains(valueToBeContained.toLowerCase())) {
				element.click();
				//System.out.println("Trying to select: "+valueToBeContained );
				return;
			}
			//		System.out.println(element.getText() );
		}
	}



	public static void runningShellCommand(String command) throws IOException, InterruptedException{
		Runtime run = Runtime.getRuntime();
		Process pr = run.exec(command);
		pr.waitFor();
		BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		while ((line=buf.readLine())!=null) {
			Log.info(line);
		}
	}
	/**
	 * method to accept alert, 
	 * exception is thrown if no alert is present
	 */
	public void acceptAlert(){
		try {
			Alert alert = driver.switchTo().alert(); 
			alert.accept();


		} catch (NoAlertPresentException e){
			throw new NoAlertPresentException();
		}	
	}


	/**
	 *  method to get message test of alert
	 * @return message text which is displayed
	 */
	public String getAlertText() 
	{ 
		try {
			Alert alert = driver.switchTo().alert(); 
			String alertText = alert.getText(); 
			return alertText; 
		} catch (NoAlertPresentException e){
			throw new NoAlertPresentException();
		}
	}   

	/**
	 *  method to verify if alert is present
	 * @return returns true if alert is present else false
	 */
	public boolean isAlertPresent() 
	{ 
		try 
		{ 
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert();
			return true; 
		}   
		catch (NoAlertPresentException e) 
		{   
			throw new NoAlertPresentException(); 
		}   
	}   


	/**
	 * method to select a value from dropdown with index
	 * @param selectLocator element with select tag
	 * @param valueToBeSelectedindex index to be selected
	 */
	public void selectValuefromDropdownviaIndex(By selectLocator, int valueToBeSelectedindex){
		Select  selectFromDropdown = new Select(findElement(selectLocator));
		selectFromDropdown.selectByIndex(valueToBeSelectedindex);

	}




}

