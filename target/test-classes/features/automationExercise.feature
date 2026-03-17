Feature: View cart page

@testDemo
Scenario Outline: View cart page
    Given user opens Chrome browser and navigate to url "<url>"
    Then user verify is on homepage auto exercise
    And user check product and add to cart with qty "<qty>"
    Then user close browser

Examples:
| url                            | qty | 
| http://automationexercise.com/ | 4   |