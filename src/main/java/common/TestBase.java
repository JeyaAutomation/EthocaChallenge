package common;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;

import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public class TestBase {
	
	private static WebDriver driver;
	private Logger Log = Logger.getLogger(getClass());
	
	@DataProvider(name = "test-data")
	public Object[][] myDataProvider(Method testMethod) throws Exception {

		Properties test = new Properties();
		try {
			FileInputStream f = new FileInputStream("src/test/resources/config/test.properties");
			test.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = test.getProperty("env.url");
		TestProperties testProperties = new TestProperties(url);
		Object[][] data = new Object[1][2];
		data[0][0] = getDriver();
		data[0][1] = testProperties;
		return data;
	}

	public WebDriver getDriver() {
		if (driver == null) {
	
			driver = new ChromeDriver();
		}
		return driver;
	}

	public <T> T initiateTestSession(WebDriver driver, TestProperties testProperties, Class<T> clazz) throws Exception{
		Log.info("Initiating the test session");
		 driver.manage().window().maximize();
		driver.get(testProperties.getUrl());
		Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[] { WebDriver.class });
	    T page = constructor.newInstance(new Object[] { driver });
	    return page;
	}
	
	@BeforeSuite(alwaysRun = true)
    public void setUp() {

		 System.setProperty("webdriver.chrome.driver","C:\\Users\\jey\\eclipse-workspace\\Challenge\\src\\main\\resources\\webdriver\\chromedriver.exe");
		 driver = new ChromeDriver();
		 initLogger();
    }
	
	 @AfterClass
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }
	 
	 public String getJsonTestDataPath(String testName)
	  {
	    String dataFolderPath = "./src/test/resources/testdata";
	    return String.format("%s/%s.json", new Object[] { dataFolderPath, testName });
	  }
	 
	 private void initLogger() {
			PropertyConfigurator.configure("Log4j.properties");
		}
}
