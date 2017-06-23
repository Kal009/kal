package alpha;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by sriramangajala on 27/08/16.
 */
public class MyStepdefs {

    static WebDriver driver;
     static URL url;

    @Before
    public void start() throws MalformedURLException {
        //System.setProperty("webdriver.chrome.driver", "C:\\Cucumbe jars\\WebDrivers\\chromedriver.exe");
        DesiredCapabilities cap = DesiredCapabilities.chrome();

        cap.setPlatform(Platform.WIN10);

         url = new URL("http://192.168.0.19:5555/wd/hub");


        driver = new RemoteWebDriver(url,cap);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(45,TimeUnit.SECONDS);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://demo.nopcommerce.com");

    }



    @Given("^User can go to home page$")
    public void User_can_go_to_home_page() throws Throwable {
        checkTitle();
    }

    @After
    public void end() {
        driver.quit();
    }

    public void checkTitle() {
        String title = driver.getTitle();
        System.out.println(title);
        Assert.assertTrue(title.equals("nopCommerce demo store"));

    }

    @When("^user registers as new user$")
    public void user_registers_as_new_user() throws Throwable {

        gotoRegisterScreen();
        enterRegisterDetails();

    }

    private void enterRegisterDetails() {

        selectGender(true);

        WebElement firstName = driver.findElement(By.name("FirstName"));
        firstName.sendKeys("Barack");

        WebElement lastName = driver.findElement(By.name("LastName"));
        lastName.sendKeys("Obama");


        WebElement day = driver.findElement(By.name("DateOfBirthDay"));
        Select day_select = new Select(day);
        day_select.selectByVisibleText("1");

        WebElement month = driver.findElement(By.name("DateOfBirthMonth"));
        Select month_select = new Select(month);
        month_select.selectByVisibleText("June");

        WebElement year = driver.findElement(By.name("DateOfBirthYear"));
        Select year_select = new Select(year);
        year_select.selectByVisibleText("1980");

        WebElement email = driver.findElement(By.id("Email"));
        email.sendKeys((new Random()).nextInt() + "_test1@test.com");

        System.out.print("Wait");
        WebElement newsletter = driver.findElement(By.id("Newsletter"));
        newsletter.click();

        String password_text = "Password1";

        WebElement password = driver.findElement(By.id("Password"));
        password.sendKeys(password_text);


        WebElement ConfirmPassword = driver.findElement(By.id("ConfirmPassword"));
        ConfirmPassword.sendKeys(password_text);


        WebElement submit = driver.findElement(By.name("register-button"));
        System.out.println("Get attribute demo-get value:" + submit.getAttribute("value"));
        submit.click();


        checkForText("Your registration completed");


        System.out.print("Wait");


    }

    public void checkForText(String text) {
        WebElement content_ui = driver.findElement(By.tagName("body"));

        System.out.println(content_ui.getText());

        Assert.assertTrue(content_ui.getText().contains(text));
    }

    public void selectGender(boolean male) {
        if (male) {
            WebElement gender_male = driver.findElement(By.id("gender-male"));
            gender_male.click();
        } else {

            WebElement gender_female = driver.findElement(By.id("gender-female"));
            gender_female.click();
        }
    }

    public void gotoRegisterScreen() {
        WebElement register_link = driver.findElement(By.className("ico-register"));
        register_link.click();
    }

    @And("^he search the product with keyword \"([^\"]*)\"$")
    public void he_search_the_product_with_keyword(String keyword) throws Throwable {

        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys(keyword);

        WebElement search_button = driver.findElement(By.xpath("//input[@value='Search']"));
        search_button.click();

    }

    @Then("^he should see equal (\\d+) result$")
    public void he_should_see_atleast_result(int expectedCount) throws Throwable {

        List<WebElement> results = driver.findElements(By.className("item-box"));

        int result_count = results.size();
        Assert.assertEquals(expectedCount, result_count);


//        for(WebElement result:results)
//        {
//         System.out.println(result.getText());
//        }

        //loop

        //get cunt

        //refer n


    }


    @Then("^no results found message is shown$")
    public void no_results_found_message_is_shown() throws Throwable {

        checkForText("No products were found that matched your criteria.");


    }

    @Then("^the ui should show message \"([^\"]*)\"$")
    public void the_ui_should_show_message(String arg1) throws Throwable {
        checkForText(arg1);
    }

    @When("^user select the first result and it should be added$")
    public void user_select_the_first_result() throws Throwable {

        List<WebElement> results = driver.findElements(By.className("item-box"));
        WebElement first_result = results.get(results.size() - 1);
        WebElement buttons = first_result.findElement(By.className("buttons"));
        WebElement add_card = buttons.findElement(By.tagName("input"));
        add_card.click();

//        Assert.assertTrue(driver.findElement(By.className("cart-qty")).getText().contains("1"));
        System.out.print("Wait");


    }

    @And("^contiue to add to basket$")
    public void contiue_to_add_to_basket() throws Throwable {

    }

    @Then("^item should be in basket$")
    public void item_should_be_in_basket() throws Throwable {

    }

    @Given("^he navigates to login page$")
    public void he_navigates_to_login_page() throws Throwable {
        driver.get("http://demo.nopcommerce.com/login");
    }

    @When("^he login with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void he_login_with_and(String arg1, String arg2) throws Throwable {
        driver.findElement(By.id("Email")).sendKeys(arg1);
        driver.findElement(By.id("Password")).sendKeys(arg2);

    }

    @And("^login as existing user$")
    public void login_as_existing_user() throws Throwable {
        driver.findElement(By.xpath("/html/body/div[6]/div[3]/div/div/div/div[2]/div[1]/div[2]/form/div[3]/input")).click();
    }

    @Then("^a error message \"([^\"]*)\" is displayed$")
    public void a_error_message_is_displayed(String expMessage) throws Throwable {
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(expMessage));
    }

    @Given("^User is in home page$")
    public void User_is_in_home_page() throws Throwable {

       driver.get("http://demo.nopcommerce.com");
    }

    @Given("^User is in home pages$")
    public void User_is_in_home_pages() throws Throwable {
      driver.get("http://demo.nopcommerce.com");
    }

    @Given("^he navigates to register page$")
    public void he_navigates_to_register_page() throws Throwable {
        driver.get("http://demo.nopcommerce.com/register");
    }

    @When("^he registers with \"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$")
    public void he_registers_with_and(String arg1, String arg2, String arg3) throws Throwable {
        WebElement firstName = driver.findElement(By.name("FirstName"));
        firstName.sendKeys(arg2);

        WebElement lastName = driver.findElement(By.name("LastName"));
        lastName.sendKeys(arg3);

    }

    @And("^fill all others details$")
    public void fillAllOthersDetails() throws Throwable {

    }

    @And("^register as new user$")
    public void registerAsNewUser() throws Throwable {

    }

    @Then("^a welcome message is displayed$")
    public void aWelcomeMessageIsDisplayed() throws Throwable {

    }

    @And("^the logout link is displayed$")
    public void theLogoutLinkIsDisplayed() throws Throwable {

    }
}
