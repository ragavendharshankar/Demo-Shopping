package gherkin_scenario_steps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.assertj.core.api.Fail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.ensure.Ensure;

public class PageObjects extends PageObject {

	public int GLOBAL_TIMEOUT = 50;
	
	@FindBy(xpath = ".//*[contains(@class,'attachment-woocommerce')]")
	private List<WebElementFacade> productLink;
	
	@FindBy(xpath = ".//*[contains(@class,'product_type_simple')]")
	private List<WebElementFacade> addCart;
	
	@FindBy(xpath = ".//*[contains(@id,'primary-menu')]/ul/li[1]")
	private WebElementFacade cart;
	
	@FindBy(xpath = ".//*[contains(@class,'woocommerce-cart-form__contents')]/tbody/tr")
	private List<WebElementFacade> listViewList;
	
	@FindBy(xpath = ".//*[contains(@class,'woocommerce-cart-form__contents')]/tbody/tr/td[4]/span")
	private List<WebElementFacade> cartList;
	
	@FindBy(xpath = ".//*[contains(@class,'woocommerce-cart-form__contents')]/tbody/tr/td[1]/a")
	private List<WebElementFacade> productList;
	
	@FindBy(xpath = ".//*[contains(@class,'woocommerce-message')]")
	private WebElementFacade alertMessage;
	
	@FindBy(xpath = ".//*[contains(@class,'woocommerce-cart-form__contents')]/tbody/tr/td[3]/a")
	private List<WebElementFacade> productName;
	
	private  String removeItem = ".//*[contains(@class,'woocommerce-cart-form__contents')]/tbody/tr/td/a[@data-product_id ='%%dataProductID%%']";
	
	@FindBy(xpath = ".//a[contains(@class,'woocommerce-loop-product__link')]/h2")
	private List<WebElementFacade> productNameDetails;
	
	
	
	Actions actions = new Actions(getDriver());
	List<String> addedProduct = new ArrayList<String>();

	public void compareCollections(List<String> expectedList, List<String> actualList) {

		for (String value : expectedList)
			Ensure.that(value).isIn(actualList);

	}

	public void findMinimum(Map<String, Double> priceList) {

		Entry<String, Double> minValue = Collections.min(priceList.entrySet(), Comparator.comparing(Entry::getValue));
		Serenity.getCurrentSession().put("minimumPriceItem", minValue.getKey());
		Serenity.getCurrentSession().put("minimumPrice", minValue.getValue());
		System.out.println( minValue.getKey());

	}
		
	public void addItems(int count) {
		
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		for(int i =0;i<count;i++) {
			actions.moveToElement(productLink.get(i));
			addCart.get(i).click();
			addedProduct.add(productNameDetails.get(i).getAttribute("innerHTML").trim());
		}
	}
	
	public void clickCart() {
				
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("window.scrollTo(document.body.scrollHeight,0)");
		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		actions.moveToElement(cart);
		cart.click();
	}
	
	public void verifyCount(int count) {
		
		Ensure.that(count).isEqualTo(listViewList.size());

	}
	
	public void viewWishList() {
		
		Map<String, Double> priceList = new HashMap<String, Double>();
		 for (int i = 0; i < cartList.size(); i++)
			 	priceList.put(productList.get(i).getAttribute("data-product_id").trim(),
		 Double.parseDouble(cartList.get(i).getText().substring(1).trim()));
		 findMinimum(priceList);
		 
	}
	
	public void removeItem() {
		
		String removeValue =  removeItem.replace("%%dataProductID%%", Serenity.getCurrentSession().get("minimumPriceItem").toString());
		getDriver().findElement(By.xpath(removeValue)).click();
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String alertMessageValue = alertMessage.getAttribute("innerHTML").trim();
		String[] deletedProduct = alertMessageValue.split("”");
		Ensure.that(alertMessageValue).containsIgnoringCase("removed");
		addedProduct.remove(deletedProduct[0].substring(1));
	}
	
	public void verifyRemainingCart() {
		
		List<String> myWishListText = new ArrayList<String>();
		for (WebElement element : productName)
			myWishListText.add(element.getAttribute("innerHTML").trim());
		compareCollections(myWishListText,addedProduct);
		
	}

}
