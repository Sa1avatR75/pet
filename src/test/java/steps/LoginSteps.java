package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utilities.Config;
import utilities.Driver;

public class LoginSteps {
    LoginPage loginPage = new LoginPage();

    @Given("user on on the login page")
    public void user_on_on_the_login_page() {
        Driver.getDriver().get(Config.getProperty("url"));
    }
    @When("user enters {string} login")
    public void user_enters_login(String login) {
        loginPage.enterLogin(login);
    }
    @When("user enters {string} password")
    public void user_enters_password(String password) {
        loginPage.enterPassword(password);
    }
    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        loginPage.clickLogin();

    }
    @Then("user should see inventory page")
    public void user_should_see_inventory_page() {
        Assert.assertNotEquals(Driver.getDriver().getCurrentUrl(), Config.getProperty("url"));
    }


}
