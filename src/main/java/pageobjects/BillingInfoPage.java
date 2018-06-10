package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;
import utils.SeleniumUtil;

public class BillingInfoPage extends BasePage {

	@FindBy(id = "current_country")
	public WebElement currentCountry;
	
	@FindBy(id = "wpsc_checkout_form_16_region")
	public WebElement currentProvince;
	
	@FindBy(xpath = "//*[@id=\"change_country\"]/input[4]")
	public WebElement calculateButton;
	
	@FindBy(id = "log")
	public WebElement username;
	
	@FindBy(id = "pwd")
	public WebElement password;
	
	@FindBy(id = "login")
	public WebElement loginButton;
	
	@FindBy(id = "wpsc_checkout_form_9")
	public WebElement email;
	
	@FindBy(id = "wpsc_checkout_form_2")
	public WebElement billingFirstName;
	
	@FindBy(id = "wpsc_checkout_form_3")
	public WebElement billingLastName;
	
	@FindBy(id = "wpsc_checkout_form_4")
	public WebElement billingAddress;
	
	@FindBy(id = "wpsc_checkout_form_5")
	public WebElement billingCity;
	
	@FindBy(id = "wpsc_checkout_form_7_region")
	public WebElement billingRegion;
	
	@FindBy(id = "wpsc_checkout_form_7")
	public WebElement billingCountry;
	
	@FindBy(id = "wpsc_checkout_form_8")
	public WebElement billingPostcode;
	
	@FindBy(id = "wpsc_checkout_form_18")
	public WebElement billingPhone;
	
	@FindBy(id = "wpsc_checkout_form_11")
	public WebElement shippingFirstName;
	
	@FindBy(id = "wpsc_checkout_form_12")
	public WebElement shippingLastName;
	
	@FindBy(id = "wpsc_checkout_form_13")
	public WebElement shippingAddress;
	
	@FindBy(id = "wpsc_checkout_form_14")
	public WebElement shippingCity;
	
	@FindBy(id = "wpsc_checkout_form_16_region")
	public WebElement shippingRegion;
	
	@FindBy(id = "wpsc_checkout_form_16")
	public WebElement shippingCountry;
	
	@FindBy(id = "wpsc_checkout_form_17")
	public WebElement shippingPostcode;
	
	@FindBy(id = "shippingSameBilling")
	public WebElement shippingSameBilling;
	

	public BillingInfoPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.waitUntilPageLoaded();
	}
	
	public TransactionResultPage confirmPurchase() {
		Log.info("Click on Purchase button and confirm the order");
		SeleniumUtil.clickElement(this.driver.findElement(By.cssSelector("input.make_purchase.wpsc_buy_button")), driver);
		return new TransactionResultPage(this.driver);
	}
}
