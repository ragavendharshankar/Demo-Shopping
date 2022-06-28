package gherkin_scenario_steps;

import org.openqa.selenium.WebDriver;

import cucumber.runner.Properties;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

public class Steps_Definition{
	
	@Steps
	ShoppingDemoSteps shoppingDemoSteps;
	
	
	@When("user navigate to the shopping page")
	public void user_navigate_to_the_shopping_page() throws InterruptedException {
		
		shoppingDemoSteps.login();
	}
	
	@When("I add {int} random items to my cart")
	public void add_random_items_to_my_cart(int count )  {
		
		shoppingDemoSteps.addRandomItems(count);
	}
	
	@When("I view my cart")
	public void viewMyCart() throws InterruptedException {
		
		shoppingDemoSteps.viewCart();
	}
	
	@Then("I find total {int} items listed in my cart")
	public void find_total_items_listed_in_my_cart(int count ) {
		
		shoppingDemoSteps.findElementsinCart(count);
	}
	
	@When("I search for lowest price item")
	public void search_for_lowest_price_item() {
		
		shoppingDemoSteps.searchLowestPrice();
	}
	
	@And("I remove the lowest price item")
	public void remove_lowest_price_item() {
		
		shoppingDemoSteps.removeLowestPriceItem();
	}
	
	@Then("I able to verify remaining items")
	public void able_to_verify_remaining_items() {
		
		shoppingDemoSteps.verifyRemaining();
	}
	
	
}
