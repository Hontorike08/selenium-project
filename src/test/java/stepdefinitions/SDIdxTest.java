package stepdefinitions;

import org.openqa.selenium.By;

import base.DriverManager;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import component.WebUI;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SDIdxTest {

    //mvn clean test -Dcucumber.filter.tags=""

    public static String globalTotalPrice;
    public static String update = "No";
    public static int grandExpectedTotal = 0;

    @Given("user opens Chrome browser and navigate to url {string}")
    public void open_chrome_and_go_to_url(String url) {
        System.out.println("URL: " + url);
        DriverManager.initDriver();
        DriverManager.getDriver().get(url);
    }

    @Given("user opens Firefox browser and navigate to url {string}")
    public void open_firefox_and_go_to_url(String url) {
        System.out.println("URL: " + url);
        DriverManager.initDriverFirefox();
        DriverManager.getDriver().get(url);
    }

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

    @And("user choose products and add to cart")
    public void user_choose_products_and_add_to_cart(DataTable table) {
        WebUI.delay(5);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String product = row.get("product").trim();
            // String size = row.getOrDefault("size", "").trim();
            String size = row.get("size");
            if (size == null || size.equalsIgnoreCase("[empty]")) {
                size = "";
            } else {
                size = size.trim();
            }
            // String color = row.getOrDefault("color", "").trim();
            String color = row.get("color");
            if (color == null || color.equalsIgnoreCase("[empty]")) {
                color = "";
            } else {
                color = color.trim();
            }

            System.out.println("Processing: " + product);
            System.out.println("Processing: " + size);
            System.out.println("Processing: " + color);

            // Click product
            WebUI.click(By.xpath("//h3[normalize-space()='" + product + "']"));
            WebUI.delay(2);

            // Verify product name
            String actualProductName = WebUI.getText(By.xpath("//h1[normalize-space()='" + product + "']"));
            if (!actualProductName.equals(product)) {
                throw new AssertionError("Product Name Not Equal. Expected: " + product + ", Got: " + actualProductName);
            }

            // Select Size
            if (!size.isEmpty()) {
                boolean isSizePresent = WebUI.verifyElementPresent("//label[normalize-space()='Size']");
                if (isSizePresent) {
                    WebUI.selectDropdownByText(By.xpath("//label[normalize-space()='Size']/following-sibling::select"), size);
                } else {
                    System.out.println("Size dropdown not available for " + product);
                }
            }

            // Select Color
            if (!color.isEmpty()) {
                boolean isColorPresent = WebUI.verifyElementPresent("//label[normalize-space()='Color']");
                if (isColorPresent) {
                    WebUI.selectDropdownByText(By.xpath("//label[normalize-space()='Color']/following-sibling::select"), color);
                } else {
                    System.out.println("Color dropdown not available for " + product);
                }
            }

            // Add to cart
            WebUI.click(By.xpath("//input[@type='submit' and @value='Add to Cart']"));
            WebUI.delay(4);

            // Back to product list
            DriverManager.getDriver().navigate().back();
            WebUI.delay(4);
        }
    }

    @Then("user go to cart page and verify product exist and qty and total")
    public void user_go_to_cart_page_and_verify_product(DataTable table) {
        WebUI.clickButtonByLabel("Check Out");
        WebUI.click(By.xpath("//a[contains(normalize-space(.), 'My Cart')]"));
        WebUI.delay(3);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String product = row.get("product").trim();
            String expectedQty = row.get("qty").trim();
            // String expectedQty = row.getOrDefault("qty", "0").trim();
            if (expectedQty == null || expectedQty.equalsIgnoreCase("[empty]")) {
                expectedQty = "";
            } else {
                expectedQty = expectedQty.trim();
            }
            // String expectedTotal = row.getOrDefault("total", "0").trim();
            String expectedTotal = row.get("total").trim();
            if (expectedTotal == null || expectedTotal.equalsIgnoreCase("[empty]")) {
                expectedTotal = "";
            } else {
                expectedTotal = expectedTotal.trim();
            }

            if(update.equals("Yes")) {
                WebUI.delay(1);

                // Get current base price
                String baseValue = WebUI.getText(By.xpath("//h3[a[contains(normalize-space(text()),'" + product + "')]]/ancestor::div[@class='row']//div[contains(@class,'price')]"));
                String price = baseValue.replace("£", "").trim();
                System.out.println("price: " + price);

                // Subtract the old total for the product from the grand total before updating the quantity
                grandExpectedTotal -= Double.parseDouble(price);

                WebUI.setText(By.xpath("//h3[a[contains(normalize-space(text()),'" + product + "')]]/ancestor::div[@class='row']//input[@name='updates[]']"), expectedQty);
                WebUI.click(By.xpath("//input[@type='submit' and @value='Update']"));
                WebUI.delay(3);
                WebUI.click(By.xpath("//a[contains(normalize-space(.), 'My Cart')]"));
            } else {
                System.out.println("Skip updating qty");
            }
            
            // Verify product exists
            System.out.println("Verifying product: " + product);
            WebUI.verifyTextContains(product);

            // Get Quantity
            String getQty = WebUI.getValue(By.xpath("//h3[a[contains(normalize-space(text()),'" + product + "')]]/ancestor::div[@class='row']//input[@name='updates[]']"));
            System.out.println("getQty: " + getQty);

            // Assert qty
            assertEquals("Qty not match for " + product, expectedQty, getQty);

            // Get Price (per item)
            String baseValue = WebUI.getText(By.xpath("//h3[a[contains(normalize-space(text()),'" + product + "')]]/ancestor::div[@class='row']//div[contains(@class,'price')]"));
            String price = baseValue.replace("£", "").trim();
            System.out.println("price: " + price);

            // Calculate expected total for the product based on qty and base price
            int calcTotal = Integer.parseInt(getQty) * (int) Double.parseDouble(price);
            System.out.println("calcTotal: " + calcTotal);
            
            // Verify total price for specific product
            assertEquals("Total price mismatch for " + product, calcTotal, Integer.parseInt(expectedTotal));

            // Add to grand total
            grandExpectedTotal += calcTotal;
        }
        WebUI.click(By.xpath("//a[contains(normalize-space(.), 'My Cart')]"));

        // Get Actual Total
        String rawActualTotal = WebUI.getText(By.xpath("//div[@class='six columns omega cart total']/h2[contains(normalize-space(.),'Total')]"));
        String actualTotal = rawActualTotal
                .replace("Total", "")
                .replace("£", "")
                .trim();

        // Total all of the expected totals for each product to get the grand total
        int actualGrandTotal = (int) Double.parseDouble(actualTotal);
        globalTotalPrice = String.valueOf(grandExpectedTotal);
        System.out.println("Expected Grand Total: " + grandExpectedTotal);
        System.out.println("Actual Grand Total: " + actualGrandTotal);
        assertEquals("Grand total mismatch!", grandExpectedTotal, actualGrandTotal);
    }

    @And("user delete product from cart and verify product not exist in cart")
    public void user_delete_product_from_cart_and_verify_product_not_exist_in_cart(DataTable table) {
        WebUI.delay(1);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String product = row.get("product").trim();
            System.out.println("Deleting product: " + product);
            WebUI.click(By.xpath("//h3[a[contains(normalize-space(text()),'" + product + "')]]/ancestor::div[@class='row']/div[@class='one column remove omega desktop']/a"));
            WebUI.delay(3);
            WebUI.click(By.xpath("//a[contains(normalize-space(.), 'My Cart')]"));
            boolean isProductPresent = WebUI.verifyElementPresent("//h3[a[contains(normalize-space(text()),'" + product + "')]]");
            if(isProductPresent) {
                throw new AssertionError("Product still present in cart after deletion: " + product);
            } else {
                System.out.println("Product successfully deleted from cart: " + product);
            }
        }
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

    @And("user checkout items")
    public void user_checkout_items() {
        WebUI.delay(5);
        WebUI.click(By.id("checkout"));
    }

    @Then("user go to cart page and update qty and verify qty and total")
    public void user_go_to_cart_page_and_update_qty_and_verify_qty_and_total(DataTable table) {
        WebUI.delay(2);
        update = "Yes";
        user_go_to_cart_page_and_verify_product(table);
    }

    @Then("user close browser")
    public void user_close_browser() {
        DriverManager.quitDriver();
    }
}
