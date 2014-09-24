Feature: Multiply

Background: 
	Given A local calculator
	And request path is "/rest/multiply"
	And request method is "POST"

Scenario: simple multiplication
	When I enter 5 and 9
	Then result is 45

Scenario: multiplying negatives
	When I enter -2.3 and -6.76
	Then result is 15.548

