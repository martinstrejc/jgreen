Feature: Cucumber-Spring support

Scenario: Create Spring context at a cucumber test
	When The glue code extends AbstractJGreenSpringBase
	And The glue code implements ApplicationContextAware interface
	Then the Spring context is autowired via a setter 

Scenario: Inject Spring Beans
	When The glue code extends AbstractJGreenSpringBase
	And The glue code implements ApplicationContextAware interface
	Then the Spring context is autowired into a Spring annotated field
	And the Spring context is autowired into a JEE annotated field

Scenario: Inject Spring Beans
    When The glue code extends AbstractJGreenSpringBase
    And The glue code implements ApplicationContextAware interface
    Then the Spring context is autowired into a Spring annotated field
    And the Spring context is autowired into a JEE annotated field

Scenario: Configuration profiles applied to properties
    Given The glue code extends AbstractJGreenSpringBase
    When jgreen.properties file overrides jgreen.name property to 'My Cucumber Test'
    Then the text 'My Cucumber Test' is injected into the configuration bean
    
Scenario: Log messages from tests
    When a message is logged via slf4j
    Then the message appears in the log4j log
    	