#Author: sogeti
@TESTS
Feature: Test beetroot on browsers.
 Esta feature realiza pruebas sobre navegadores.

@TestSearch
Scenario Outline: F001 - Busqueda google.
Given I navigate to page "https://www.sogeti.com"
 When I search for "<busqueda>"
 Then I verify the presence of link with text "<nombre>"
  And I close the navigator
	
 Examples:
   | busqueda | nombre                     | 
   |  WiseQA  | capgemini-digital-testing  | 
