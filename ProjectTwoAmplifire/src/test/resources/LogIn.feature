Feature: user login

	# you can use this when all of your scenarios have a certain prerequisite
	# Background: the user is not logged in

	Scenario: successful login
		Given the user is on the homepage
		When the user enters the correct username
		And the user enters the correct password
		And the user clicks the login button
		Then the nav will show the user's first name
	
	Scenario: register
		Given the user is on the homepage
		When the user doesn't have a username
		Then the nav will direct to register page
		
	Scenario: username does not exist
		Given the user is on the homepage
		When the user enters an incorrect username
		And the user clicksthe login button
		Then an incorrect credentials message will be displayed
		
	Scenario: incorrect password
		Given the user is on the homepage
		When the user enters the correct username
		And the user enters the incorrect password
		And the user clicks the login button
		Then an incorrect credentials message will be displayed 
		
	Scenario: List of Books Available
		Given the user is on the homepage
		When the user has logged in
		Then the list of available books will be shown
		
	Scenario: search Book by title
		Given the user is on the homepage
		When the user has logged in
		And the user can see a list of available books 
		Then the user can make request for book
		
	Scenario: checkout book
		Given the user is on the homepage
		When the user has logged in
		And the user can see a list of available books
		And the user has made a request for a book
		And the request is accepted
		Then issue book to the user
	
	Scenario Outline: invalid input
		Given the user is on the homepage
		When the user enters the username "<username>"
		And the user enters the password "<password>"
		And the user clicks the login button
		Then an invalid input message will be displayed
		
		Examples:
			|	username | password |
			|	a				 | p4ssw0rd |
			| user123	 | a			  |