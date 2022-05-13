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
	
	
	@When("user login to the shopping page")
	public void user_login_to_the_shopping_page() throws InterruptedException {
		
		shoppingDemoSteps.login();
	}
	
	@Given("I add four different products to my wish list")
	public void I_add_four_different_products_to_my_wish_list(DataTable dataTable) throws InterruptedException {
		shoppingDemoSteps.enterProducts(dataTable);
		
	}
	
	@When("I view my wish list table")
	public void I_view_my_wish_list_table() {
		shoppingDemoSteps.viewWishList();
	}
	
	@Then("I found total four selected items in my wish list")
	public void I_found_total_four_selected_items_in_my_wish_list() {
		shoppingDemoSteps.findSelectedItems();
	}
	
	@When("I search for lowest price product")
	public void I_search_for_lowest_price_product() {
		
		shoppingDemoSteps.searchLowestProduct();
	}
	
	@And("I am able to add the lowest price item to my cart")
	public void I_am_able_to_add_the_lowest_price_item_to_my_cart() {
		
		shoppingDemoSteps.addLowestPriceItemtoCart();
	}
	
	@Then("I am able to verify the item in my cart")
	public void I_am_able_to_verify_the_item_in_my_cart() {
		
		shoppingDemoSteps.verifyIteminCart();
	}
	
	
}
