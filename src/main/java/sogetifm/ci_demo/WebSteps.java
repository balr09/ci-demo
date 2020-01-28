package sogetifm.ci_demo;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
	private DesiredCapabilities capabilities = new DesiredCapabilities();


	public WebSteps() {
	}
	
	@Given("^I navigate to page \"([^\"]*)\"$")
	public void i_navigate_to_page(String url) throws Throwable {
		driver = buildWebDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		wait = new WebDriverWait(driver, 20);
	    driver.navigate().to(url);
	}

	protected WebDriver buildWebDriver() {
		String browser = System.getProperty("selenium.browser") == null
				? (System.getenv("SELENIUM_BROWSER") == null ? "chrome"
						: System.getenv("SELENIUM_BROWSER").toLowerCase())
				: System.getProperty("selenium.browser").toLowerCase();

		switch (browser) {
		case "chrome":
			browser = "chrome";
			break;
		case "firefox":
			browser = "firefox";
			break;
		case "internet explorer":
		case "ie":
		case "explorer":
		default:
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			browser = "internet explorer";
			break;
		}

		// setPlatform("WINDOWS");
		capabilities.setBrowserName(browser);
		
		driver = startRemoteDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		wait = new WebDriverWait(driver, 15);
		return driver;
	}
	
	protected WebDriver startRemoteDriver() {
		String server = System.getProperty("selenium.server") == null
				? (System.getenv("SELENIUM_SERVER") == null ? "localhost"
						: System.getenv("SELENIUM_SERVER").toLowerCase())
				: System.getProperty("selenium.server").toLowerCase();

		String port = System.getProperty("selenium.port") == null
				? (System.getenv("SELENIUM_PORT") == null ? "4444" : System.getenv("SELENIUM_PORT").toLowerCase())
				: System.getProperty("selenium.port").toLowerCase();

		URL url = null;
		try {
			url = new URL("http://" + server + ":" + port + "/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		driver = new RemoteWebDriver(url, capabilities);
		return driver;
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
