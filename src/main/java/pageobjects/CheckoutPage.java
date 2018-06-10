package pageobjects;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import common.BasePage;
import enums.Item;
import utils.SeleniumUtil;

public class CheckoutPage extends BasePage {

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitUntilPageLoaded();
		waitForItemsToLoad();
	}

	private void waitForItemsToLoad() {
		if (!this.isCartEmpty()) {
			SeleniumUtil.waitForElementToAppear(driver, By.id("checkout_page_container"));
		}
	}

	public void verifyItemQuantitiesAndPrice(HashMap<String, Integer> itemList) throws Exception {
		Log.info("Verifying checkout page displays right items with right quantities and prices");
		SoftAssert softAssert = new SoftAssert();

		// checking number of items is same as expected
		int actualNumberOfItems = this.driver.findElement(By.cssSelector("table.checkout_cart"))
				.findElements(By.tagName("tr")).size() - 1;
		int expectedNumberOfItems = itemList.size();
		softAssert.assertEquals(actualNumberOfItems, expectedNumberOfItems,
				String.format("Expected  '%d' items to be displayed on the checkout page but'%d' items found",
						actualNumberOfItems, expectedNumberOfItems));

		for (int i = 0; i < actualNumberOfItems; i++) {
			String productName = this.driver
					.findElement(By.cssSelector(String.format("td.wpsc_product_name.wpsc_product_name_%d", i)))
					.getText().trim();

			// checking the quantity of each item is same as expected
			String actualQuantity = this.driver
					.findElement(By.cssSelector(String.format("td.wpsc_product_quantity.wpsc_product_quantity_%d", i)))
					.findElement(By.name("quantity")).getAttribute("value").trim();
			String expectedQuantity = itemList.get(productName).toString();
			softAssert.assertTrue(actualQuantity.equals(expectedQuantity),
					String.format("Expected the quantity of '%s' to be '%s' but found '%s'", productName,
							expectedQuantity, actualQuantity));

			// checking the total price of each item is same as expected
			double actualprice = Double.parseDouble(this.driver
					.findElement(By.cssSelector(String.format("td.wpsc_product_price.wpsc_product_price_%d", i)))
					.getText().trim().replace("$", ""));
			double expectedPrice = Item.getItemPriceByName(productName) * Double.parseDouble(expectedQuantity);
			softAssert.assertEquals(actualprice, expectedPrice,
					String.format("Expected the total price of '%s' to be '%s' but found '%s'", productName,
							expectedPrice, actualprice));

		}
		softAssert.assertAll();

	}
	
	
	public BillingInfoPage continueToBillingInfo() {
		Log.info("Click continue and navigate to billing information");
		SeleniumUtil.clickElement(
				this.driver.findElement(By.xpath("//*[@id=\"checkout_page_container\"]/div[1]/a/span")), driver);
		return new BillingInfoPage(this.driver);
	}

}
