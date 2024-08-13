Feature: login functionality

  Scenario: login on Sauce Demo and verify logged in
    Given user on on the login page
    When user enters "standard_user" login
    And user enters "secret_sauce" password
    And user clicks on login button
    Then user should see inventory page

