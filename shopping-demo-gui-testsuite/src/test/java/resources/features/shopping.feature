Feature: shopping_based_on_price_feature 

	Scenario: Verify whether user can able to add item in cart based on price
		When user login to the shopping page
		Given I add four different products to my wish list
		|productSearch|
		|Polo T-shirt|
		|Single Shirt|
		|Evening trousers|
		|modern|
		When I view my wish list table
		Then I found total four selected items in my wish list
		When I search for lowest price product
		And I am able to add the lowest price item to my cart
		Then I am able to verify the item in my cart
	
		
	
	