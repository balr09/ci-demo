package sogetifm.ci_demo;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


/**
 * Hello world!
 *
 */
public class WebSteps 
{
	private WebDriver driver;
	private WebDriverWait wait;
	
	public WebSteps() {
	}
	
	@Given("^I navigate to page \"([^\"]*)\"$")
	public void i_navigate_to_page(String url) throws Throwable {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		wait = new WebDriverWait(driver, 20);
	    driver.navigate().to(url);
	}

	@When("^I search for \"([^\"]*)\"$")
	public void i_search_for(String searchString) throws Throwable {
		By by = By.xpath("//header//a[contains(@href, 'search')]");
		
		WebElement sb = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		sb.click();
		
		driver.findElement(By.id("query")).sendKeys(searchString+Keys.ENTER);
	}

	@Then("^I verify the presence of link with text \"([^\"]*)\"$")
	public void i_verify_the_presence_of_link_with_text(String text) throws Throwable {
		String xpath = "//a[contains(., '"+text+"')]";
	    WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

	    assertNotNull(link);
	}

	@Then("^I close the browser$")
	public void i_close_the_browser() throws Throwable {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@AfterClass
	public void afterClass() throws Throwable {
		i_close_the_browser();
	}
}
