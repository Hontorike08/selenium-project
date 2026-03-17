package stepdefinitions;

import org.openqa.selenium.By;

import base.DriverManager;

import static org.junit.Assert.assertEquals;

import component.WebUI;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class SDIdxTest {

    public static String globalTotalPrice;

    @Then("user verify is in Create Account page")
    public void user_verify_is_in_create_account_page() {
        String xpath = "//h1[@class='accounts-title' and text()='Create Account']";
        String actualMsg = WebUI.getText(By.xpath(xpath));
        String msg = "SUCCESS: User is in Create Account page";
        assertEquals(msg, "Create Account", actualMsg);
        System.out.println("User is in Create Account page");
    }

    @Then("user verify is in Login page")
    public void user_verify_is_in_login_page() {
        String xpath = "//h1[@class='accounts-title' and text()='Customer Login']";
        String actualMsg = WebUI.getText(By.xpath(xpath));
        String msg = "Text is not same as expected";
        assertEquals(msg, "Customer Login", actualMsg);
        System.out.println("User is in Customer Login page");
    }

    @And("user input first name {string}")
    public void user_input_first_name(String firstName) {
        WebUI.setTextByName("customer[first_name]", firstName);
        WebUI.delay(10);
    }

    @And("user input last name {string}")
    public void user_input_last_name(String lastName) {
        WebUI.setTextByName("customer[last_name]", lastName);
        WebUI.delay(10);
    }

    @And("user input email address {string}")
    public void user_input_email_address(String email) {
        WebUI.setTextByName("customer[email]", email);
        WebUI.delay(5);
    }

    @And("user input password {string}")
    public void user_input_password(String password) {
        WebUI.setTextByName("customer[password]", password);
        WebUI.delay(2);
    }

    @And("user press button create")
    public void user_press_button_create() {
        WebUI.delay(1);
        WebUI.click(By.xpath("//input[@type='submit' and @value='Create']"));
        WebUI.delay(2);
    }

    @And("user press button sign in")
    public void user_press_button_sign_in() {
        WebUI.delay(1);
        WebUI.click(By.xpath("//input[@type='submit' and @value='Sign In']"));
        WebUI.delay(2);
    }

    @Then("user verify error message {string}")
    public void user_verify_error_message(String errorMsg) {
        String xpath = "//li[normalize-space()='" + errorMsg + "']";
        String actualMsg = WebUI.getText(By.xpath(xpath));
        String msg = "SUCCESS: Error message is displayed correctly: '" + errorMsg + "'";
        assertEquals(msg, errorMsg, actualMsg);
    }

    @Then("user verify on my account page")
    public void user_verify_on_my_account_page() {
        String xpath = "//h1[@class='accounts-title' and text()='Account Details and Order History']";
        WebUI.verifyElementPresent(xpath);
        String actualMsg = WebUI.getText(By.xpath(xpath));
        String msg = "SUCCESS: User is on My Account page";
        assertEquals(msg, "Account Details and Order History", actualMsg);
        System.out.println("User is on My Account page");
    }

    @And("user choose product {string} and add to cart")
    public void user_choose_product_and_add_to_cart(String product) {
        // WebUI.clickButtonByLabel(product);
        WebUI.click(By.xpath("//h3[normalize-space()='" + product + "']"));
        WebUI.delay(3);
        String actualProdcutName = WebUI.getText(By.xpath("//h1[normalize-space()='" + product + "']"));
        assertEquals("Product Name Not Equal", product, actualProdcutName);
        // WebUI.clickButtonByText("Add to cart");
        WebUI.click(By.xpath("//input[@type='submit' and @value='Add to Cart']"));
        WebUI.delay(3);
    }

    @Then("user go to cart page, verify product {string} exist and qty {string} and total {string} and checkout item")
    public void user_go_to_cart_page_and_checkout_item(String product, String qty, String total) {
        // WebUI.click(By.id("shopping_cart_container"));
        WebUI.clickButtonByLabel("Check Out");
        WebUI.delay(3);
        WebUI.click(By.xpath("//a[contains(normalize-space(.), 'My Cart')]"));
        // WebUI.verifyTextPresent(product);
        WebUI.verifyTextContains(product);
        String getQty = WebUI.getValue(By.xpath("//input[@type='text' and @name='updates[]']"));
        System.out.println("getQty: " + getQty);
        String rawTotal = WebUI.getText(By.xpath("//span[contains(normalize-space(), '£" + total + "')]"));
        String getTotal = rawTotal.replace("£", "").trim();
        System.out.println("getTotal: " + getTotal);
        int gTotal = Integer.parseInt(getQty) * (int) Double.parseDouble(getTotal);
        System.out.println("gTotal: " + gTotal);
        String rawActualTotal = WebUI.getText(By.xpath("//h2[contains(normalize-space(), '£" + gTotal + "')]"));
        String actualTotal = rawActualTotal.replace("Total", "").replace("£", "").trim();
        System.out.println("actualTotal: " + actualTotal);
        globalTotalPrice = String.valueOf(gTotal);
        assertEquals("Total price is not correct", gTotal, (int) Double.parseDouble(actualTotal));
        WebUI.click(By.xpath("//a[contains(normalize-space(.), 'My Cart')]"));
        WebUI.delay(3);
        WebUI.click(By.id("checkout"));
    }

    @And("user verify on payment page and fill email {string} and first name {string} and last name {string} and company {string} and address {string} and city {string} and province {string} and postal code {string} and phone {string} and card number {string} and expiry month and year {string} and cvv {string} and name on card {string} and click pay now")
    public void user_verify_on_payment_page_and_fill_payment_details(String email, String firstName, String lastName, String company, String address, String city, String province, String postalCode, String phone, String cardNumber, String expiryDate, String cvv, String nameOnCard) {
        WebUI.delay(5);
        WebUI.getText(By.xpath("//strong[contains(normalize-space(), '£" + globalTotalPrice + "')]"));
        WebUI.setTextByPlaceholder("Email", email);
        WebUI.setTextByPlaceholder("First name (optional)", firstName);
        WebUI.setTextByPlaceholder("Last name", lastName);
        WebUI.setTextByPlaceholder("Company (optional)", company);
        WebUI.setTextByPlaceholder("Address", address);
        WebUI.setTextByPlaceholder("City", city);
        WebUI.selectDropdownByText(By.name("zone"), province);
        WebUI.setTextByPlaceholder("Postal code", postalCode);
        WebUI.setTextByPlaceholder("Phone (optional)", phone);
        WebUI.delay(5);

        System.out.println("Input Card Details");
        WebUI.switchToIframe(By.xpath("//iframe[@title='Field container for: Card number']"));
        WebUI.setTextByPlaceholder("Card number", cardNumber);
        System.out.println("Input Card Number");
        DriverManager.getDriver().switchTo().defaultContent();

        WebUI.switchToIframe(By.xpath("//iframe[@title='Field container for: Expiration date (MM / YY)']"));
        WebUI.setTextSlowlyByPlaceholder("Expiration date (MM / YY)", expiryDate);
        WebUI.delay(1);
        DriverManager.getDriver().switchTo().defaultContent();

        WebUI.switchToIframe(By.xpath("//iframe[@title='Field container for: Security code']"));
        WebUI.setTextByPlaceholder("Security code", cvv);
        WebUI.delay(1);
        DriverManager.getDriver().switchTo().defaultContent();
        
        // WebUI.switchToIframe(By.xpath("//iframe[@title='Field container for: Name on card']"));
        // WebUI.setTextByPlaceholder("Name on card", nameOnCard);
        // WebUI.delay(1);
        // DriverManager.getDriver().switchTo().defaultContent();
        
        WebUI.delay(3);
        WebUI.click(By.id("checkout-pay-button"));
    }

}
