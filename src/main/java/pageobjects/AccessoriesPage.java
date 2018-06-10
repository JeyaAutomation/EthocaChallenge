package pageobjects;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;
import enums.Item;
import enums.ProductView;
import utils.SeleniumUtil;



public class AccessoriesPage extends BasePage {
	
	@FindBy(xpath = "//*[@id=\"checkout_page_container\"]/div[1]/a/span")
	private WebElement continueButton;
	
	private HashMap<String, Integer> itemList;
	
	public AccessoriesPage(WebDriver driver) {
		this.driver = driver;
		itemList = new HashMap<String,Integer>();
		PageFactory.initElements(driver, this);
		waitUntilPageLoaded();
	}
	
	public void addAnItemToCart(Item item, ProductView view, int quantity) {
		
		String itemContainer = view == ProductView.list? String.format("div.default_product_display.%s.accessories.group", item.getItemReference()) : String.format("div.product_grid_item.%s",item.getItemReference());
	    WebElement itemContainerElement = this.driver.findElement(By.cssSelector(itemContainer));
	    WebElement addToCartButton = itemContainerElement.findElement(By.cssSelector("input.wpsc_buy_button"));
	    for(int i = 0; i < quantity; i++) {
	    Log.info(String.format("Clicking on 'AddToCart' button and adding %s to cart...", item.getItemName()));
	    SeleniumUtil.clickElement(addToCartButton, driver);
	    this.waitUntilPageLoaded();
	    loadItemList(item.getItemName());
	    SeleniumUtil.pause(1000);
	    }
	}
	
	public void switchToGridView() {
		Log.info("Changing the product view to grid");
		SeleniumUtil.clickElement(this.driver.findElement(By.cssSelector("a.grid")), driver);
	}
	
	public void switchToListView() {
		Log.info("hanging the product view to list");
		SeleniumUtil.clickElement(this.driver.findElement(By.cssSelector("a.default")), driver);
	}
	
	private void loadItemList(String itemName) {
		if(!itemList.containsKey(itemName)) {
			itemList.put(itemName,1);
        } else {
        	itemList.put(itemName, itemList.get(itemName) +1);
        }
	}
	
	public HashMap<String, Integer> getItemList(){
		return this.itemList;
	}


}

