package pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import framework.CommonMethods;

/**
 * class to interact with page
 * @author shanky
 *
 * This class has web elements of homepage which are used by HomePageHelper
 */
public class Homepage extends CommonMethods {

	
	public Homepage(WebDriver driver) throws IOException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	public By searchbox = By.cssSelector("#lst-ib");
	public By searchButton = By.cssSelector("#sblsbb > button > span");
	public By searchResults = By.cssSelector("#rso > div > div > div > h3 > a");
	public By imagesButton = By.cssSelector("#hdtb-msb > div:nth-child(3) > a");
	public By imageContainer = By.cssSelector("#rg_s > div:nth-child(1) > a > img");
	
	
	
	
	
	
	
	
}
