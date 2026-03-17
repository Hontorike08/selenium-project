Feature: Orange HRM

@orangeHRM
Scenario Outline: User login to Orange HRM
    Given user opens Chrome browser and navigate to url "<url>"
    And user input username "<username>"
    And user input password "<password>"
    And user press button login
    And user see dashboard page
    Then user close browser

Examples:
| url                                                                | username | password |
| https://opensource-demo.orangehrmlive.com/web/index.php/auth/login | Admin    | admin123 |

@orangeHRM-add
Scenario Outline: User add new candidate in Orange HRM
    Given user opens Chrome browser and navigate to url "<url>"
    And user input username "<username>" and password "<password>" and login
    Then user navigate to PIM menu and add new employee with first name "<firstName>", middle name "<midName>" and last name "<lastName>"
    Then user close browser

Examples:
| url                                                                | username | password | firstName | midName | lastName |
| https://opensource-demo.orangehrmlive.com/web/index.php/auth/login | Admin    | admin123 | Yahoo     | Yahaa   | Yihii    |


# @testSauceDemo
# Scenario Outline:
#     Given user opens Chrome browser and navigate to url "<url>"
#     And user input username "<username>"
#     And user input password "<password>"
#     And user press button login
#     And user choose product "<product>" and add to cart
#     Then user go to cart page, verify product "<product>" exist and checkout item
#     Then user close browser

# Examples:
# | url                        | username      | password     | product             |
# | https://www.saucedemo.com/ | standard_user | secret_sauce | Sauce Labs Backpack |
