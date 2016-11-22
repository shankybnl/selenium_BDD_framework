@scenario2


Feature: selenium bdd frameworks images on google
  
 
 Scenario: search selenium bdd frameworks images on google search

Given user is on google search page "https://www.google.co.in"
And searches for "selenium bdd framework"
And user clicks on search button
When user clicks on image tab
Then the results images should be displayed