package com.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import common.TestListener;
import common.TestBase;
import common.JsonDataManager;
import common.TestDataManager;
import common.TestProperties;
import enums.Item;
import enums.Navigation;
import enums.ProductView;
import pageobjects.AccessoriesPage;
import pageobjects.BillingInfoPage;
import pageobjects.CheckoutPage;
import pageobjects.HomePage;
import pageobjects.TransactionResultPage;

@Listeners({ TestListener.class })

public class ShoppingCartTests extends TestBase {

	@Test(dataProvider = "test-data")
	public void createOrder_forOneMagicMouse_createdSuccessfully(WebDriver driver, TestProperties testProperties)
			throws Exception {

		String jsonTestData = "billing_info_data";
		TestDataManager tdm = JsonDataManager.loadData(getJsonTestDataPath(jsonTestData));

		HomePage homePage = this.initiateTestSession(driver, testProperties, HomePage.class);
		AccessoriesPage accessoriesPage = homePage.navigateToPageViaMenuOptions(Navigation.PRODUCT_ACCESSORIES,
				AccessoriesPage.class);
		accessoriesPage.addAnItemToCart(Item.MAGIC_MOUSE, ProductView.list, 1);
		Assert.assertEquals(accessoriesPage.getItemCountFromTheCart(), 1,
				String.format("Expected the number of items in the cart to be 1 but found %d",
						accessoriesPage.getItemCountFromTheCart()));
		CheckoutPage checkoutPage = accessoriesPage.proceedToCheckout();
		checkoutPage.verifyItemQuantitiesAndPrice(accessoriesPage.getItemList());
		BillingInfoPage billingInfoPage = checkoutPage.continueToBillingInfo();
		billingInfoPage.fillForm(tdm);
		TransactionResultPage transResulPage = billingInfoPage.confirmPurchase();
		transResulPage.confirmOrderedItems(accessoriesPage.getItemList());
		transResulPage.confirmOrderMessage();
		Assert.assertEquals(transResulPage.getItemCountFromTheCart(), 0,
				String.format("Expected the number of items in the cart to be 0 after placing the order successfully but found %d",
						transResulPage.getItemCountFromTheCart()));

	}

	@Test(dataProvider = "test-data")
	public void createOrder_forMultipleItems_createdSuccessfully(WebDriver driver, TestProperties testProperties)
			throws Exception {

		String jsonTestData = "billing_info_data";
		TestDataManager tdm = JsonDataManager.loadData(getJsonTestDataPath(jsonTestData));

		HomePage homePage = this.initiateTestSession(driver, testProperties, HomePage.class);
		AccessoriesPage accessoriesPage = homePage.navigateToPageViaMenuOptions(Navigation.PRODUCT_ACCESSORIES,
				AccessoriesPage.class);
		accessoriesPage.addAnItemToCart(Item.MAGIC_MOUSE, ProductView.list, 1);
		accessoriesPage.addAnItemToCart(Item.APPLE_TV, ProductView.list, 2);
		CheckoutPage checkoutPage = accessoriesPage.proceedToCheckout();
		checkoutPage.verifyItemQuantitiesAndPrice(accessoriesPage.getItemList());
		BillingInfoPage billingInfoPage = checkoutPage.continueToBillingInfo();
		billingInfoPage.fillForm(tdm);
		TransactionResultPage transResulPage = billingInfoPage.confirmPurchase();
		transResulPage.confirmOrderedItems(accessoriesPage.getItemList());
		transResulPage.confirmOrderMessage();
		Assert.assertEquals(transResulPage.getItemCountFromTheCart(), 0,
				String.format("Expected the number of items in the cart to be 0 after placing the order successfully but found %d",
						transResulPage.getItemCountFromTheCart()));

	}
}
