# EcoDashboard Selenium Test Suite

Automated UI test suite for validating RSS feed card functionality in the EcoDashboard web application.

This project uses Selenium WebDriver, TestNG, and Allure Reporting to verify that RSS feed cards are rendered correctly and contain valid content on the application's homepage.

---

## Overview

The test suite performs end-to-end validation of the EcoDashboard feed card components by checking:

- Feed cards are displayed on the homepage
- Feed card titles are present and non-empty
- Feed card links contain valid URLs

The framework follows the **Page Object Model (POM)** design pattern to separate page interactions from test logic, making the test suite easier to maintain and extend.

---

## Tech Stack

- Java 11
- Selenium WebDriver 4
- TestNG
- Maven
- WebDriverManager
- Allure Reports
- Google Chrome

---

## Project Structure

```text
ecodashboard-selenium-tests/
│
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── pages/
│   │   │   │   └── HomePage.java
│   │   │   │
│   │   │   └── tests/
│   │   │       ├── BaseTest.java
│   │   │       └── FeedCardsTest.java
│   │   │
│   │   └── resources/
│   │       └── testng.xml
│
├── allure-results/
├── pom.xml
└── README.md
```

---

## Framework Design

### BaseTest

Responsible for:

- WebDriver initialization
- Browser setup
- Test environment configuration
- Driver cleanup after execution

The application under test is configured to run locally:

```java
http://localhost:5173
```

---

### HomePage

Page Object responsible for:

- Retrieving feed cards
- Retrieving feed card titles
- Retrieving feed card links
- Managing page-level interactions

Element synchronization is handled through explicit waits.

---

### FeedCardsTest

Contains functional validations for RSS feed cards.

Implemented test cases:

| Test Case | Description |
|------------|-------------|
| Feed Cards Present | Verifies at least one feed card is displayed |
| Feed Card Titles Not Empty | Verifies every feed card contains a title |
| Feed Card Links Valid | Verifies every feed card contains a valid URL |

---

## Prerequisites

Before running the tests, ensure the following are installed:

- Java 11 or higher
- Maven 3.8 or higher
- Google Chrome

### Start the Application

The EcoDashboard application must be running locally:

```text
http://localhost:5173
```

Launch the application before executing the test suite.

---

## Installation

Clone the repository:

```bash
git clone https://github.com/your-username/ecodashboard-selenium-tests.git
cd ecodashboard-selenium-tests
```

Install dependencies:

```bash
mvn clean install
```

---

## Running Tests

Execute all tests:

```bash
mvn clean test
```

The suite will run using the TestNG configuration located at:

```text
src/test/resources/testng.xml
```

---

## Allure Reporting

Generate test results:

```bash
mvn test
```

Serve the Allure report:

```bash
allure serve allure-results
```

Generate a static report:

```bash
allure generate allure-results --clean
allure open
```

---

## Sample Assertions

### Feed Card Presence

```java
Assert.assertFalse(cards.isEmpty(), "No feed cards found");
```

### Title Validation

```java
Assert.assertFalse(
    title.getText().trim().isEmpty(),
    "A feed card has an empty title"
);
```

### URL Validation

```java
Assert.assertTrue(
    href.startsWith("http"),
    "Invalid URL: " + href
);
```

---

## Current Test Coverage

- RSS feed card visibility
- Feed card title validation
- Feed card URL validation
- Basic UI verification
