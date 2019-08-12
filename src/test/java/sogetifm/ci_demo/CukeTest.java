package sogetifm.ci_demo;

import java.io.File;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {	//"pretty",
				"html:target/reports/HTML",
				"json:target/reports/JSON/cucumber.json",
				"junit:target/reports/JUNIT/cucumber.xml",
				"com.cucumber.listener.ExtentCucumberFormatter:target/reports/ExtentReports/report.html"
			},
		monochrome = true,
		features = "features",
		tags = {"@TESTS"},
		glue = "sogetifm.ci_demo"
)
public class CukeTest {
	@AfterClass
    public static void teardown() {
        Reporter.loadXMLConfig(new File("config/extent-config.xml"));
        
//        Reporter.setSystemInfo("user", System.getProperty("user.name"));
//        Reporter.setTestRunnerOutput("Sample test runner output message <br>");
    }
}
