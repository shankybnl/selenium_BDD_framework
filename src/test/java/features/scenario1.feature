@scenario1


Feature: search for selenium bdd frameworks on google and save the title and url of the results in CSV file
  
 
 Scenario: search selenium bdd frameworks on google

Given user is on google search page "https://www.google.co.in"
And searches for "selenium bdd framework"
When user clicks on search button
Then the results should be displayed and saved in csv file