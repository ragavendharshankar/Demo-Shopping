package gherkin_scenario_steps;

import java.util.List;

import org.openqa.selenium.WebDriver;

import cucumber.runner.Properties;
import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class ShoppingDemoSteps  {
	
	PageObjects pageObjects;

	@Step
	public void login() {
		
		pageObjects.getDriver().get(Properties.url);
		pageObjects.getDriver().manage().window().maximize();

	}
	
	@Step
	public void addRandomItems(int count) {
		
		pageObjects.addItems(count);
		
	}
	
	@Step
	
	public void viewCart() {
		pageObjects.clickCart();
		
	}
	
	@Step
	public void findElementsinCart(int count) {
		
		pageObjects.verifyCount(count);
		
	}
	
	@Step
	
	public void searchLowestPrice() {
		
		pageObjects.viewWishList();
	}
	
	@Step
	public void removeLowestPriceItem() {
		
		pageObjects.removeItem();
	}
	
	@Step
	public void verifyRemaining() {
		
		pageObjects.verifyRemainingCart();
	}
	
	
	

}
