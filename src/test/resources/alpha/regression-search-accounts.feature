Feature: Regression pack - search and accounts module

  Background:
    Given User is in home pages


  Scenario Outline: Test Scenario - Invalid Login with various data combo

    Given User is in home page
    Given he navigates to login page
    When he login with "<username>" and "<password>"
    And login as existing user
    Then a error message "<message>" is displayed

    Examples:

      | username          | password  | message                |
      | testuser@test.com |           | Login was unsuccessful |
      |                   | password1 |                        |
      | test              | password1 |                        |
      | test@t            | password1 |                        |
      |                   |           |                        |
      | 1212              | 1212      |                        |


  Scenario: Acceptance Critera - Register as new user

#    Steps
#    Given //precondition//opening home page
#    And //extension of previous step
#    When //test steps
#    And // more test steps
#    Then //expected and actual result comparision //asserts
#    And //extension of previous step
#    // any test data should be in double quotes

#    Given User is in home page
    Given he navigates to register page
    When he registers with "Mr","Rama" and "Rao"

  Scenario: Acceptance Critera - Register as new user

    Given he navigates to register page
    When he registers with "Mr","Rama" and "Rao"
    And fill all others details
    And register as new user
    Then a welcome message is displayed
    And the logout link is displayed

  Scenario: Acceptance Critera - Login as existing user

    Given he navigates to login page
    When he login with "testuser@test.com" and "password1"
    And login as existing user
    Then a welcome message is displayed
    And the logout link is displayed
    And user can see homepage

  Scenario Outline: Search multiple

    Then User can go to home page
    Then user registers as new user
    Then the ui should show message "Your registration completed"
    And he search the product with keyword "<keyword>"
    Then he should see equal <count> result
    When user select the first result and it should be added

    Examples:

      | keyword | count |
      | windows | 1     |
      | laptop  | 2     |


  Scenario Outline: Search multiple with no results

    Then User can go to home page
    Then user registers as new user
    And he search the product with keyword "<keyword>"
    Then he should see equal <count> result
    Then no results found message is shown
    Then the ui should show message "No products were found that matched your criteria"

    Examples:

      | keyword | count |
      | iphone  | 0     |











