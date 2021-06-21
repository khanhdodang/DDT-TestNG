import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ExcelExample {
    WebDriver driver;

    @BeforeClass
    void SetUp() {
        // System.setProperty("webdriver.chrome.driver", "Path of Chrome Driver");
        driver = new ChromeDriver();
    }

    @Test(dataProvider = "testdata")
    public void demoClass(String username, String password) throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//button")).click();
        Thread.sleep(5000);
        Assert.assertTrue(driver.getTitle().matches("The Internet"), "Invalid credentials");
        System.out.println("Login successful");
    }

    @AfterClass
    void TearDown() {
        driver.quit();
    }

    @DataProvider(name = "testdata")
    public Object[][] testDataExample() {
        ReadExcelFile configuration = new ReadExcelFile("src/main/resources/Workbook1.xlsx");
        int rows = configuration.getRowCount(0);
        Object[][] signin_credentials = new Object[rows][2];

        for (int i = 0; i < rows; i++) {
            signin_credentials[i][0] = configuration.getData(0, i, 0);
            signin_credentials[i][1] = configuration.getData(0, i, 1);
        }
        return signin_credentials;
    }
}