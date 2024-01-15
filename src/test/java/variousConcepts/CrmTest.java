package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CrmTest {

	WebDriver driver;
	String environment;
	String browser;
	//Login Data
	
	String username  ;
	String password  ;
	 
//Element List
	By USERNAME_FIELD = By.xpath("//*[@id=\"user_name\"] ");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("//*[@id=\"login_submit\"]");
	By DASHBOARD_HEADER_FIELD = By.xpath("//h3[contains(text(),'Desposit Vs Expense')]");

 
	// TestData / mock Data
	String dashboardValidationText = "Desposit Vs Expense";
	String userValidationText = "Please enter your user name";
    String alertValidationText = "Please enter your password";
  
    @BeforeClass
 
    public void readConfig() {
    	 
    //bufferReader//inputStream//FileReader//Scanner
    	 try {
    		 InputStream input = new FileInputStream("/Users/nabil/eclipse-level1/TestNg/src/main/java/config/config.properties");
    		 
    		 Properties prop = new Properties();
    		 prop.load(input);
    		 environment = prop.getProperty("url");
		     username =  prop.getProperty("username");
		     password = prop.getProperty("password");
	         browser = prop.getProperty("browser");
	         
	}catch(IOException e) {
		e.printStackTrace();
	}
	}
    @BeforeMethod
	public void setUp() {
	
    	
    	
    	
    	if(browser.equalsIgnoreCase("chrome")) {
    		
    		System.setProperty("webdriver.chrome.driver", "/Users/nabil/chromeDriver/chromedriver");
    		driver = new ChromeDriver();
    		
    	
    	}else if(browser.equalsIgnoreCase("edge")) {
    	
		System.setProperty("webdriver.edge.driver", "/Users/nabil/Documents/msedgedriver");
		driver = new EdgeDriver();
    	}else {
    		System.out.println("please use a valide browser ");
    	}
	
    	
    	driver.manage().deleteAllCookies();
		driver.get(environment);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test
	public void lognTest() {

		driver.findElement(USERNAME_FIELD).sendKeys(username);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
        Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboardValidationText, "dashboard not found !");
	}
	 @Test
	public void testAlertLoginPage() {
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		String actualAlertUserText = driver.switchTo().alert().getText();
		Assert.assertEquals(actualAlertUserText,  userValidationText, "Alert user msg is not found !");
		driver.switchTo().alert().accept();
		driver.findElement(USERNAME_FIELD).sendKeys(username);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.switchTo().alert().getText(), alertValidationText,  "Alert msg not found !!");
		driver.switchTo().alert().accept();
	}
	
	
	//@AfterMethod
    public void teaDown() {
    	driver.close();
    	driver.quit();
    }
}