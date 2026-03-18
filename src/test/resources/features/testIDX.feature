Feature: Functional Test

@testIDX-create-account-negative
Scenario Outline: Create Account (Negative)
    Given user opens Chrome browser and navigate to url "https://sauce-demo.myshopify.com/account/register"
    Then user verify is in Create Account page
    And user input first name "<firstName>"
    And user input last name "<lastName>"
    And user input email address "<email>"
    And user input password "<password>"
    And user press button create
    Then user verify error message "<errorMsg>"
    Then user close browser

Examples:
| firstName | lastName | email                   | password | errorMsg                                        |
| John      | Doe      | najyvu2497@guysmail.com | 123      | Password is too short (minimum is 5 characters) |

@testIDX-login
Scenario Outline: Login
    Given user opens Chrome browser and navigate to url "https://sauce-demo.myshopify.com/account/login"
    Then user verify is in Login page
    And user input email address "<email>"
    And user input password "<password>"
    And user press button sign in
    Then user verify on my account page
    Then user close browser

Examples:
| email                   | password  |
| tetexe422@inboxbear.com | test12345 |

@testIDX-delete-from-cart
Scenario Outline: Delete item from cart
    Given user opens Firefox browser and navigate to url "https://sauce-demo.myshopify.com/"
    Then user choose products and add to cart
        | product     | size | color |
        | Grey jacket |      |       |
        | Noir jacket | M    | Red   |
        | Striped top |      |       |
    Then user go to cart page, verify product exist and qty and total
        | product     | qty | total |
        | Grey jacket | 1   | 55    |
        | Noir jacket | 1   | 60    |
        | Striped top | 1   | 50    |
    And user delete product from cart and verify product not exist in cart
        | product     |
        | Grey jacket |
        | Noir jacket |
    Then user close browser

@testIDX-add-to-cart
Scenario Outline: Add item to cart
    Given user opens Chrome browser and navigate to url "https://sauce-demo.myshopify.com/"
    And user choose product "<product>" and add to cart
    Then user go to cart page, verify product "<product>" exist and qty "<qty>" and total "<total>" and checkout item
    And user verify on payment page and fill email "<email>" and first name "<firstName>" and last name "<lastName>" and company "<company>" and address "<address>" and city "<city>" and province "<province>" and postal code "<postalCode>" and phone "<phone>" and card number "<cardNumber>" and expiry month and year "<expiryDate>" and cvv "<cvv>" and name on card "<firstname>" and click pay now
    Then user close browser

Examples:
| product     | qty | total | email                   | firstName | lastName | company    | address     | city      | province  | postalCode | phone          | cardNumber          | expiryDate | cvv  |
| Grey jacket | 1   | 55    | tetexe422@inboxbear.com | John      | Doe      | Test Inc   | 123 Test St | Test City | West Java | 12345      | 0821-1054-5855 | 5552 0000 8888 8080 | 1227       | 123  |


@testIDX-update-qty
Scenario Outline: Update item quantity in cart
    Given user opens Chrome browser and navigate to url "https://sauce-demo.myshopify.com/"
    And user choose product "<product>" and add to cart
    Then user go to cart page, verify product "<product>" exist and update qty "<qty>" and see total "<total>" and checkout item
    And user verify on payment page and fill email "<email>" and first name "<firstName>" and last name "<lastName>" and company "<company>" and address "<address>" and city "<city>" and province "<province>" and postal code "<postalCode>" and phone "<phone>" and card number "<cardNumber>" and expiry month and year "<expiryDate>" and cvv "<cvv>" and name on card "<firstname>" and click pay now
    Then user close browser

Examples:
| product     | qty | total | email                   | firstName | lastName | company    | address     | city      | province  | postalCode | phone          | cardNumber          | expiryDate | cvv  |
| Grey jacket | 2   | 110   | tetexe422@inboxbear.com | John      | Doe      | Test Inc   | 123 Test St | Test City | West Java | 12345      | 0821-1054-5855 | 5552 0000 8888 8080 | 1227       | 123  |