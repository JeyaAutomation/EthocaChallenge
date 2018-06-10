package utils;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumUtil
{
  private static Logger Log = Logger.getLogger("SeleniumUtil");
  
  
  public static void pause(long ms)
  {
    try
    {
      Thread.sleep(ms);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
 

  public static void waitForElementToBeClickable(WebDriver driver, WebElement webElement)
  {
    WebDriverWait wdw = new WebDriverWait(driver, 60L);
    wdw.withMessage(String.format("Timed out waiting for the element '%s' to be clickable.", new Object[] {
      webElement.toString() }));
 
    wdw.until(ExpectedConditions.elementToBeClickable(webElement));
  }
  
  public static boolean isElementPresentOnPage(WebDriver driver, By by)
  {
    try
    {
      driver.findElement(by);
    }
    catch (NoSuchElementException e)
    {
      return false;
    }
    catch (StaleElementReferenceException e)
    {
      return false;
    }
    return true;
  }
  
  public static boolean isElementVisible(WebDriver driver, By by)
  {
    try
    {
      WebElement el = driver.findElement(by);
      return el.isDisplayed();
    }
    catch (NoSuchElementException e)
    {
      return false;
    }
    catch (StaleElementReferenceException e)
    {
      return false;
    }
    catch (WebDriverException e) {}
    return false;
  }
  
  public static boolean isEmptyOrZeroValue(WebElement webElement)
  {
    String value = webElement.getAttribute("value");
    return (value.isEmpty()) || ("0.00".equals(value)) || ("0,00".equals(value));
  }
  
  
  
  public static String executeJavaScript(WebDriver driver, String js)
    throws Exception
  {
    Log.info("Executing JavaScript");
    if ((driver instanceof JavascriptExecutor)) {
      return (String)((JavascriptExecutor)driver).executeScript(js, new Object[0]);
    }
    throw new Exception("Unable to execute JavaScript on the driver. forceClick failed.");
  }
  
 
  
  public static boolean isElementVisibleOnPage(WebElement elm)
  {
    try
    {
      return elm.isDisplayed();
    }
    catch (NoSuchElementException e)
    {
      Log.info(String.format("%s, which means element is not present", e.getMessage()));
      return false;
    }
    catch (StaleElementReferenceException e)
    {
      Log.info(String.format("%s, which means element is not present", e.getMessage()));
    }
    return false;
  }
  
  public static boolean isAlertPresent(WebDriver driver)
  {
    try
    {
      driver.switchTo().alert();
      return true;
    }
    catch (NoAlertPresentException Ex)
    {
      Log.error(Ex.getMessage());
    }
    return false;
  }
  
  public static void scrollToElement(WebElement element, WebDriver driver)
  {
    JavascriptExecutor jse = (JavascriptExecutor)driver;
    
    String js = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);var elementTop = arguments[0].getBoundingClientRect().top;window.scrollBy(0, elementTop-(viewPortHeight/2));";
    

    jse.executeScript(js, new Object[] { element });
  }
  
  public static void clickElement(WebElement element, boolean scrollToElement, WebDriver driver)
  {
    
    if (scrollToElement) {
      scrollToElement(element,driver);
    }
   
      element.click();
   
  }
  
  public static void clickElement(WebElement element, WebDriver driver)
  {
    clickElement(element, true, driver);
  }
  
  public static void waitForElementToAppear(WebDriver driver, By by)
  {
    WebDriverWait wdw = new WebDriverWait(driver, 60L);
    wdw.withMessage(
      String.format("Timed out waiting for the element with property '%s' to appear.", new Object[] { by.toString() }));
    
    wdw.until(ExpectedConditions.presenceOfElementLocated(by));
  }
}
