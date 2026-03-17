Feature: Add item(s) to cart

@testSauceDemo
Scenario Outline:
    Given user opens Chrome browser and navigate to url "<url>"
    And user input username "<username>"
    And user input password "<password>"
    And user press button login
    And user choose product "<product>" and add to cart
    Then user go to cart page, verify product "<product>" exist and checkout item
    Then user close browser

Examples:
| url                        | username      | password     | product             |
| https://www.saucedemo.com/ | standard_user | secret_sauce | Sauce Labs Backpack |