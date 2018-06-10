package pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);

		waitUntilPageLoaded();
		
	}
}

