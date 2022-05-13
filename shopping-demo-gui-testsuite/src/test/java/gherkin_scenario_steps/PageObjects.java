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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.ensure.Ensure;

public class PageObjects extends PageObject {

	public int GLOBAL_TIMEOUT = 50;

	@FindBy(xpath = "//*[@class='header-search-form']//input[@name ='s']")
	private WebElementFacade searchBar;

	@FindBy(xpath = "//*[@class='header-search-form']//button[@class ='header-search-button']")
	private WebElementFacade searchButton;

	@FindBy(xpath = "//*[@class='yith-wcwl-icon fa fa-heart-o'][1]")
	private WebElementFacade addWishList;

	@FindBy(xpath = "//*[@class='lar la-heart']")
	private WebElementFacade viewWishList;

	@FindBy(xpath = ".//*[contains(@class,'shop_table')]/tbody/tr/td[3]/a")
	private List<WebElementFacade> myWishList;

	@FindBy(xpath = "//*[contains(@class,'shop_table')]/tbody/tr/td[4]/ins/span/bdi")
	private List<WebElementFacade> priceList1;
	
	@FindBy(xpath = ".//*[contains(@class,'shop_table')]/tbody/tr/td[4]/span/bdi") 
	private List<WebElementFacade> priceList2;
	
	@FindBy(xpath = "//*[@name='add-to-cart']")
	private WebElementFacade addToCart;
	
	@FindBy(xpath = "//a[@title ='Cart'][1]/i")
	private WebElementFacade viewCart;
	
	
	 

	public void searchProduct(String value) throws InterruptedException {
		typeInElement(searchBar, value);
		searchButton.click();
		clickOnElement(addWishList);
	}

	public void typeInElement(WebElementFacade webElement, String value) {

		webElement.withTimeoutOf(GLOBAL_TIMEOUT, TimeUnit.SECONDS).waitUntilVisible();
		webElement.withTimeoutOf(GLOBAL_TIMEOUT, TimeUnit.SECONDS).waitUntilClickable();
		webElement.click();
		webElement.type(value);
	}

	public void clickOnElement(WebElementFacade webElement) {

		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if (webElement.isVisible())
			webElement.click();
	}

	public void clickWishList() {

		clickOnElement(viewWishList);
	}

	public void validateWishList() {

		List<String> addedList = (List<String>) Serenity.getCurrentSession().get("datatable");
		List<String> myWishListText = new ArrayList<String>();
		getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		// List<WebElement> myWishList =
		// getDriver().findElements(By.xpath(".//*[contains(@class,'shop_table')]/tbody/tr/td[3]/a"));
		for (WebElement element : myWishList)
			myWishListText.add(element.getAttribute("innerHTML").trim());
		compareCollections(addedList, myWishListText);

	}

	public void searchLowestPrice() {
		Map<String, Double> priceList = new HashMap<String, Double>();
		for (int i = 0; i < priceList1.size(); i++)
			priceList.put(myWishList.get(i).getAttribute("innerHTML").trim(), Double.parseDouble(priceList1.get(i).getText().substring(1).trim()));
		for (int i = 0; i < priceList2.size(); i++)
			priceList.put(myWishList.get(i).getAttribute("innerHTML").trim(), Double.parseDouble(priceList2.get(i).getText().substring(1).trim()));
		findMinimum(priceList);

	}

	public void compareCollections(List<String> expectedList, List<String> actualList) {

		for (String value : expectedList)
			Ensure.that(value).isIn(actualList);

	}

	public void findMinimum(Map<String, Double> priceList) {

		Entry<String, Double> minValue = Collections.min(priceList.entrySet(), Comparator.comparing(Entry::getValue));
		Serenity.getCurrentSession().put("minimumPriceItem", minValue.getKey());
		Serenity.getCurrentSession().put("minimumPrice", minValue.getValue());
		getDriver().findElement(By.partialLinkText(minValue.getKey())).click();

	}
	
	public void addtoCart() {
		
		getDriver().findElement(By.partialLinkText(Serenity.getCurrentSession().get("minimumPriceItem").toString())).click();
		clickOnElement(addToCart);
	}
	
	public void verifyinCart() {
		
		//getDriver().navigate().refresh();
		getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].click();", viewCart);
		Ensure.that(Serenity.getCurrentSession().get("minimumPriceItem").toString()).isEqualTo(myWishList.get(0).getAttribute("innerHTML").trim());
		Ensure.that(Serenity.getCurrentSession().get("minimumPrice").toString()).isEqualTo(priceList2.get(0).getText().substring(1).trim());
		
	}

}
