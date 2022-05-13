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
	public void enterProducts(DataTable dataTable) throws InterruptedException {
		
		List<String> mapVal = dataTable.asList();
		Serenity.getCurrentSession().put("datatable", mapVal.subList(1, mapVal.size()));
		for(int i=1;i<mapVal.size();i++) {
			
			pageObjects.searchProduct(mapVal.get(i));
		}
	}
	
	@Step
	public void viewWishList()
	{
		pageObjects.clickWishList();
	}
	
	@Step
	public void findSelectedItems()
	{
		pageObjects.validateWishList();
	}
	
	@Step
	public void searchLowestProduct() {
		
		pageObjects.searchLowestPrice();

	}
	
	@Step
	public void addLowestPriceItemtoCart() {
		
		pageObjects.addtoCart();

	}
	
	@Step
	public void verifyIteminCart() {
		
		pageObjects.verifyinCart();

	}
	
	
	

}
