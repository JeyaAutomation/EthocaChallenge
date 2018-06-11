package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import common.BasePage;
import enums.Item;

public class TransactionResultPage extends BasePage {

	public TransactionResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.waitUntilPageLoaded();
	}

	public void confirmOrderedItems() throws NumberFormatException, Exception {
		Log.info("Verifying Transaction result page displays right items with right quantities and prices");
		SoftAssert softAssert = new SoftAssert();
		int actualNumberOfItems = this.driver.findElement(By.cssSelector("table.wpsc-purchase-log-transaction-results"))
				.findElements(By.tagName("tr")).size() - 1;

		for (int i = 0; i < actualNumberOfItems; i++) {
			String productName = this.driver
					.findElement(
							By.xpath(String.format("//*[@id=\"post-30\"]/div/div[2]/table/tbody/tr[%d]/td[1]", i + 1)))
					.getText();

			// checking quantity of each ordered item is same as checkout
			String actualQuantity = this.driver
					.findElement(
							By.xpath(String.format("//*[@id=\"post-30\"]/div/div[2]/table/tbody/tr[%d]/td[3]", i + 1)))
					.getText();
			String expectedQuantity = itemList.get(productName).toString();
			softAssert.assertTrue(actualQuantity.equals(expectedQuantity),
					String.format(
							"Expected the quantity of '%s' to be '%s' but found '%s' on the Transaction Result Page",
							productName, expectedQuantity, actualQuantity));

			// checking the total price of each item
			double actualPrice = Double.parseDouble(this.driver
					.findElement(
							By.xpath(String.format("//*[@id=\"post-30\"]/div/div[2]/table/tbody/tr[%d]/td[4]", i + 1)))
					.getText().trim().replace("$", ""));
			double expectedPrice = Item.getItemPriceByName(productName) * Double.parseDouble(expectedQuantity);
			softAssert.assertEquals(actualPrice, expectedPrice,
					String.format(
							"Expected the total price of '%s' to be '%s' but found '%s' on the Transaction Result Page",
							productName, expectedPrice, actualPrice));
		}

		softAssert.assertAll();
	}

	public void confirmOrderMessage() {
		String msg = this.driver.findElement(By.cssSelector("div.entry-content")).getText();
		Assert.assertTrue(msg.contains("Thank you for purchasing with ONLINE STORE"),
				"Expected Transactio Result page to contain a thank you message.");
	}

}
