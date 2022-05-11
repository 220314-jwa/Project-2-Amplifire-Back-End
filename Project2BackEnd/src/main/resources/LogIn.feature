Feature: User Login
  I want to use this template for my feature file

  Scenario: Successful Login
    Given the user is on the homepage 
    When the user enters the correct username
    And the user enters the correct password
    And the user clicks on the login button
    Then the nav will show the user's first name.
  
  Scenario: Incorrect Username 
     Given the user is on the homepage
     When the user enters an incorrect username
     And the user enters enter the correct password
     And the user clicks on the login button 
     Then the incorrect credentials message will be displayed.
        
  Scenario: Incorrect password
    Given the user is on the homepage
    When the user enters the correct username
    And the user enters an incorrect password
    And the user clicks on the login button 
    Then the incorrect credentials message will be displayed. 
 
    Examples: 
      | username | password | status  |
      | wil |  wilodeo | success |
      | 675 |  wouldoe| Fail    |
      |wil | 6753 | Fail
