package gherkin_scenario_steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.webdriver.DriverSource;
import cucumber.runner.Properties;

public class CustomDriver implements DriverSource {
	
	public transient WebDriver driver;

	String driverPath = System.getProperty("user.dir");
	@Override
	public WebDriver newDriver() {
		
		WebDriver driver = null;
		switch(Properties.execution_browser) {
		case "chrome":
			driver = chromeDriver(driver);	
		break;
		case "firefox":
			driver = new FirefoxDriver();
		break;
		default:
			driver = chromeDriver(driver);
		}

	return driver;
	}
		
	public WebDriver chromeDriver(WebDriver driver) {
		
		
		System.setProperty("wedriver.chrome.driver", driverPath+"\\"+Properties.ChromeDriverPath);
		ChromeOptions chrOptions = new ChromeOptions();
		chrOptions.setExperimentalOption("useAutomationExtension", false);
		driver = new ChromeDriver(chrOptions);
		return driver;
	}
	
	public WebDriver getDriver() {
		
		return (driver != null) ? driver : Serenity.getWebdriverManager().getWebdriver();
	}

	@Override
	public boolean takesScreenshots() {
		// TODO Auto-generated method stub
		return true;
	}

}
