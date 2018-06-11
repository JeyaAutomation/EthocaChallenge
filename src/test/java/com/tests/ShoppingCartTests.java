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
		checkoutPage.verifyItemQuantitiesAndPrice();
		BillingInfoPage billingInfoPage = checkoutPage.continueToBillingInfo();
		billingInfoPage.fillForm(tdm);
		TransactionResultPage transResulPage = billingInfoPage.confirmPurchase();
		transResulPage.confirmOrderedItems();
		transResulPage.confirmOrderMessage();
		Assert.assertEquals(transResulPage.getItemCountFromTheCart(), 0, String.format(
				"Expected the number of items in the cart to be 0 after placing the order successfully but found %d",
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
		checkoutPage.verifyItemQuantitiesAndPrice();
		BillingInfoPage billingInfoPage = checkoutPage.continueToBillingInfo();
		billingInfoPage.fillForm(tdm);
		TransactionResultPage transResulPage = billingInfoPage.confirmPurchase();
		transResulPage.confirmOrderedItems();
		transResulPage.confirmOrderMessage();
		Assert.assertEquals(transResulPage.getItemCountFromTheCart(), 0, String.format(
				"Expected the number of items in the cart to be 0 after placing the order successfully but found %d",
				transResulPage.getItemCountFromTheCart()));

	}

	@Test(dataProvider = "test-data")
	public void updateQuantity_fromCheckoutPage_updatedSuccessfully(WebDriver driver, TestProperties testProperties)
			throws Exception {

		HomePage homePage = this.initiateTestSession(driver, testProperties, HomePage.class);
		AccessoriesPage accessoriesPage = homePage.navigateToPageViaMenuOptions(Navigation.PRODUCT_ACCESSORIES,
				AccessoriesPage.class);
		accessoriesPage.addAnItemToCart(Item.MAGIC_MOUSE, ProductView.list, 1);

		CheckoutPage checkoutPage = accessoriesPage.proceedToCheckout();
		checkoutPage.increaseQuantityByOne(Item.MAGIC_MOUSE);
		Assert.assertEquals(checkoutPage.getItemCountFromTheCart(), 2, String.format(
				"Expected the number of items in the cart to be 2 after updating the quantity successfully but found %d",
				checkoutPage.getItemCountFromTheCart()));

	}

	@Test(dataProvider = "test-data")
	public void removeAnItem_ffromCheckoutPage_removedSuccessfully(WebDriver driver, TestProperties testProperties)
			throws Exception {

		HomePage homePage = this.initiateTestSession(driver, testProperties, HomePage.class);
		AccessoriesPage accessoriesPage = homePage.navigateToPageViaMenuOptions(Navigation.PRODUCT_ACCESSORIES,
				AccessoriesPage.class);
		accessoriesPage.addAnItemToCart(Item.MAGIC_MOUSE, ProductView.list, 1);
		accessoriesPage.addAnItemToCart(Item.APPLE_TV, ProductView.list, 2);
		CheckoutPage checkoutPage = accessoriesPage.proceedToCheckout();
		checkoutPage.removeAnItemFromTheList(Item.APPLE_TV);
		Assert.assertEquals(1, checkoutPage.countNumberOfProductsInCheckoutPage(),
				String.format(
						"Expected only 1 product to be displayed in the checkout page but %d products was displayed",
						checkoutPage.countNumberOfProductsInCheckoutPage()));
		Assert.assertEquals(1, checkoutPage.getItemCountFromTheCart(),
				String.format("Expected only 1 item to be displayed in the cart but %d items were displayed",
						checkoutPage.getItemCountFromTheCart()));
	}
}
