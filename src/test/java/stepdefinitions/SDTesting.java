package stepdefinitions;

import component.WebUI;

import org.openqa.selenium.By;

import base.DriverManager;
import io.cucumber.java.en.*;

//mvn clean test -Dcucumber.filter.tags=""

public class SDTesting {

    @Given("user opens Chrome browser and navigate to url {string}")
    public void open_chrome_and_go_to_url(String url) {
        System.out.println("URL: " + url);
        DriverManager.initDriver();
        DriverManager.getDriver().get(url);
        // boolean verifLoginPage = WebUI.verifyElementPresent("//h5[normalize-space()='Login']");
        // while(!verifLoginPage) {
        //     System.out.println("Waiting to enter login page");
        //     verifLoginPage = WebUI.verifyElementPresent("//h5[normalize-space()='Login']");
        // }
    }

    @Given("user opens Firefox browser and navigate to url {string}")
    public void open_firefox_and_go_to_url(String url) {
        System.out.println("URL: " + url);
        DriverManager.initDriverFirefox();
        DriverManager.getDriver().get(url);
    }

    @And("user input username {string}")
    public void user_input_username(String username) {
        WebUI.setTextByPlaceholder("Username", username);
    }

    @And("user press button login")
    public void user_press_login() {
        // WebUI.clickButtonByText("Login");
        WebUI.clickLoginButton();
    }

    @And("user see dashboard page")
    public void user_see_dashboard_page() {
        boolean verifDashboard = WebUI.verifyElementPresent("//span[normalize-space()='Dashboard']");
        while(!verifDashboard) {
            System.out.println("Waiting to enter dashboard page");
            verifDashboard = WebUI.verifyElementPresent("//span[normalize-space()='Dashboard']");
        }

        if(verifDashboard) {
            System.out.println("Dashboard is present");
        } else {
            WebUI.stopIfFalse(verifDashboard, "Dashboard is not present");
        }
    }

    @Then("user close browser")
    public void user_close_browser() {
        DriverManager.quitDriver();
    }

    @And("user input username {string} and password {string} and login")
    public void user_input_username_and_password(String username, String password) {
        user_input_username(username);
        // user_input_password(password);
        user_press_login();
        user_see_dashboard_page();
    }

    @Then("user navigate to PIM menu and add new employee with first name {string}, middle name {string} and last name {string}")
    public void user_add_new_employee(String firstName, String midName, String lastName) {
        WebUI.clickNavbar("PIM");
        boolean verifPIM = WebUI.verifyElementPresent("//h6[normalize-space()='PIM']");
        while(!verifPIM) {
            System.out.println("Waiting to enter PIM page");
            verifPIM = WebUI.verifyElementPresent("//h6[normalize-space()='PIM']");
        }

        WebUI.clickButtonByLabel("Add Employee");
        boolean verifAddEmployee = WebUI.verifyElementPresent("//h6[normalize-space()='Add Employee']");
        while(!verifAddEmployee) {
            System.out.println("Waiting to enter Add Employee page");
            verifAddEmployee = WebUI.verifyElementPresent("//h6[normalize-space()='Add Employee']");
        }

        WebUI.setTextByPlaceholder("First Name", firstName);
        WebUI.setTextByPlaceholder("Middle Name", midName);
        WebUI.setTextByPlaceholder("Last Name", lastName);
        String name = firstName + " " + lastName;

        WebUI.clickButtonByText("Save");
        boolean verifEmployeeAdded = WebUI.verifyElementPresent("//h6[normalize-space()='" + name + "']");
        while(!verifEmployeeAdded) {
            System.out.println("Waiting to enter Employee " + name + " personal detail page");
            verifEmployeeAdded = WebUI.verifyElementPresent("//h6[normalize-space()='" + name + "']");
        }

        WebUI.takeScreenshot("addEmployee");
    }

    @Then("user verify is on homepage auto exercise")
    public void user_verify_is_on_homepage() {
        WebUI.verifyElementPresent("//a[normalize-space()='Home']");
        WebUI.delay(1);
    }

    @And("user check product and add to cart with qty {string}")
    public void user_check_product(String qty) {
        // String rawPrice = WebUI.getText(By.xpath("//p[normalize-space()='Blue Top']/preceding-sibling::h2"));
        WebUI.click(By.xpath("//p[normalize-space()='Blue Top']/ancestor::div[@class='product-image-wrapper']//a[normalize-space()='View Product']"));
        System.out.println("Close ad");
        WebUI.closeAdIfPresent();
        WebUI.delay(1);
        // WebUI.click(By.xpath("//div[contains(@style,'cursor') and .//svg]"));
        WebUI.verifyElementPresent("//h2[normalize-space()='Blue Top']");
        // String cartPrice = WebUI.getText(By.xpath("//label[normalize-space()='Quantity:']/preceding-sibling::span"));
        
        WebUI.delay(5);
        WebUI.setTextByName("quantity", qty);
        WebUI.delay(3);
        WebUI.clickButtonByText("Add to cart");
        WebUI.delay(2);
        WebUI.clickButtonByLabel("View Cart");
        WebUI.delay(2);
        String rawText = WebUI.getText(By.xpath("//td[contains(@class,'cart_quantity')]//button"));
        if(rawText.equals(qty)) {
            System.out.println("Qty is correct");
        } else {
            System.out.println("Qty is incorrect. Actual qty: " + rawText);
        }
    }


}
