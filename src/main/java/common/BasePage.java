package common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

import pageobjects.CheckoutPage;
import pageobjects.HomePage;
import enums.Navigation;
import utils.SeleniumUtil;

public class BasePage {

	public Logger Log = Logger.getLogger(getClass());

	@FindBy(className = "count")
	private WebElement itemCount;

	@FindBy(id = "header_cart")
	private WebElement checkoutButton;

	public WebDriver driver;

	public <T> T navigateToPageViaMenuOptions(Navigation navMenuItem, Class<T> clazz) throws Exception {
		String[] linksToClick = navMenuItem.getClickableIDs();
		Log.info(String.format("Navigating to %s via navigation menu", navMenuItem.getTitle()));

		Actions action = new Actions(this.driver);
		if (linksToClick.length == 1) {

			SeleniumUtil.clickElement(this.driver.findElement(By.id(linksToClick[0])), this.driver);

		} else if (linksToClick.length == 2) {

			action.moveToElement(this.driver.findElement(By.id(linksToClick[0])));
			action.moveToElement(this.driver.findElement(By.id(linksToClick[1]))).click().build().perform();

		} else {
			throw new Exception(
					String.format("List of clickable IDs for %s is not valid. Please review Navigation.java.",
							new Object[] { navMenuItem.getTitle() }));
		}
		waitUntilPageLoaded();
		return this.getInitializedClass(clazz);

	}

	public HomePage navigateToHomePage() throws Exception {
		return navigateToPageViaMenuOptions(Navigation.HOME, HomePage.class);

	}

	public <T> T getInitializedClass(Class<T> clazz) throws Exception {
		T page = null;
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[] { WebDriver.class });
			page = constructor.newInstance(new Object[] { this.driver });
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return page;
	}

	public int getItemCountFromTheCart() {
		return Integer.parseInt(itemCount.getText().trim());

	}

	public boolean isCartEmpty() {
		return itemCount.getText().trim().equals("0");
	}

	public CheckoutPage proceedToCheckout() {
		SeleniumUtil.clickElement(this.checkoutButton, driver);
		return new CheckoutPage(driver);
	}

	public void waitUntilPageLoaded() {
		Log.info("Waiting for page to load");
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			SeleniumUtil.pause(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	public void fillForm(TestDataManager tdm) throws Exception {

		fillForm(tdm.getData(getClass()));

	}

	public void fillForm(LinkedHashMap<String, FieldData> data) throws Exception {
		Class<?> formType = getClass();
		Log.info(String.format("Attempting to fill the form of type '%s'", formType));

		for (Map.Entry<String, FieldData> d : data.entrySet()) {
			String fieldName = (String) d.getKey();

			FieldData fieldData = (FieldData) d.getValue();
			try {
				Field field = formType.getDeclaredField(fieldName);
				WebElement el = (WebElement) field.get(this);
				inputDataIntoField(el, fieldData.value, field.getName());
			} catch (Exception ex) {

				throw ex;
			}

		}
	}

	public void inputDataIntoField(WebElement el, String dataToInput, String fieldName) throws Exception {

		String tagName = el.getTagName();

		if (tagName.equals("select")) {

			Log.info(String.format(" Selecting value '%s' for dropdown field '%s'.", dataToInput, fieldName));
			Select dropdown = new Select(el);

			dropdown.selectByVisibleText(dataToInput);

		} else if (tagName.equals("textarea")) {
			Log.info(String.format("  Inputting value '%s' for textarea field '%s'.", dataToInput, fieldName));

			SeleniumUtil.scrollToElement(el, this.driver);
			el.clear();
			SeleniumUtil.clickElement(el, false, this.driver);

			el.sendKeys(new CharSequence[] { dataToInput });
			el.sendKeys(new CharSequence[] { Keys.TAB });
		} else if (tagName.equals("input")) {
			String inputType = el.getAttribute("type");
			if ((inputType.equals("text")) || (inputType.equals("email")) || (inputType.equals("password"))) {
				Log.info(String.format("  Inputting value '%s' for textbox field '%s'.", dataToInput, fieldName));

				SeleniumUtil.scrollToElement(el, this.driver);
				el.clear();
				SeleniumUtil.clickElement(el, false, this.driver);

				el.sendKeys(new CharSequence[] { dataToInput });
				el.sendKeys(new CharSequence[] { Keys.TAB });
			}

			else if (inputType.equals("checkbox")) {
				Log.info(String.format("  Selecting checkbox field '%s'.", fieldName));
				if ((!el.isSelected()) && (dataToInput.equals("1"))) {
					SeleniumUtil.clickElement(el, false, this.driver);

				} else if ((el.isSelected()) && (dataToInput.equals("0"))) {
					SeleniumUtil.clickElement(el, false, this.driver);
				}
			}
		}
	}

	
}
