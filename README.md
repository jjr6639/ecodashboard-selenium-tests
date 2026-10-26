# EcoDashboard Selenium Test Suite

[![EcoDashboard Selenium Suite](https://github.com/jjr6639/ecodashboard-selenium-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/jjr6639/ecodashboard-selenium-tests/actions/workflows/ci.yml)

Automated UI tests for the RSS feed cards on the homepage of [EcoDashboard](https://github.com/jjr6639/ecodashboard).

The suite uses Selenium WebDriver, TestNG and Allure. It runs locally against a dev server, and in GitHub Actions on every push and pull request to `main`. Each CI run publishes its Allure report to GitHub Pages.

**Latest test report:** https://jjr6639.github.io/ecodashboard-selenium-tests/

---

## What the suite checks

| Test | What it verifies |
|------|------------------|
| `feedCardsArePresentTest` | At least one feed card (`.news-card`) renders on the homepage |
| `feedCardTitlesAreNotEmptyTest` | Every card title (`.news-card h3`) found on the page has non-empty text |
| `feedCardLinksAreValidTest` | Every card link (`a.news-card`) with an `href` points to an `http`/`https` URL. Cards with no link are skipped, because some RSS items have no URL. That is a feed data issue, not a UI bug. |

The tests run against live RSS data from the app's API, so results depend on the feeds being reachable when the suite runs.

---

## Tech stack

- Java 11
- Selenium WebDriver 4
- TestNG
- Maven
- WebDriverManager
- Allure Reports
- Google Chrome (headless in CI)
- GitHub Actions + GitHub Pages

---

## Project structure

```text
ecodashboard-selenium-tests/
├── .github/
│   └── workflows/
│       └── ci.yml              # CI: start the app, run the suite, publish the report
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/
│       │   │   └── HomePage.java       # Page Object: locators and waits
│       │   └── tests/
│       │       ├── BaseTest.java       # Driver setup and teardown
│       │       └── FeedCardsTest.java  # Test cases
│       └── resources/
│           └── testng.xml              # Suite definition
├── pom.xml
└── README.md
```

`allure-results/` and `target/` are created when the tests run.

---

## Framework design

The suite follows the **Page Object Model**: page locators and interactions live in `HomePage`, and the test classes only hold assertions.

### BaseTest

- Creates a fresh Chrome session before each test and quits it afterward
- Opens the app at `http://localhost:5173`
- Runs Chrome headless with a 1920×1080 viewport when `-Dheadless=true` is passed

### HomePage

- Holds the locators for feed cards, titles and links
- Waits up to 15 seconds for the feed cards to appear before returning elements, using explicit waits rather than fixed sleeps

### FeedCardsTest

- Contains the three tests listed above, tagged with Allure `@Feature`, `@Severity` and `@Description` annotations

---

## Running locally

### Prerequisites

- Java 11 or higher
- Maven 3.8 or higher
- Google Chrome
- The [EcoDashboard](https://github.com/jjr6639/ecodashboard) app running locally: frontend on `http://localhost:5173`, API on port 3001

### Run the tests

```bash
git clone https://github.com/jjr6639/ecodashboard-selenium-tests.git
cd ecodashboard-selenium-tests
mvn clean test
```

To run headless, as CI does:

```bash
mvn clean test -Dheadless=true
```

The suite is defined in `src/test/resources/testng.xml`.

### View the Allure report

You need the [Allure command-line tool](https://allurereport.org/docs/install/) for these commands.

```bash
# Build a temporary report and open it in the browser
allure serve allure-results

# Or generate a static report and open it
allure generate allure-results --clean
allure open
```

---

## Continuous integration

`.github/workflows/ci.yml` runs on pushes and pull requests to `main`, and can also be started manually. Each run:

1. Checks out this repo and the [EcoDashboard app repo](https://github.com/jjr6639/ecodashboard)
2. Installs and starts the API (port 3001) and the Vite frontend (port 5173) in the background
3. Waits until both respond (`/api/health` and the frontend), with a 60-second timeout
4. Runs the suite in headless Chrome with `mvn test -Dheadless=true`
5. Uploads the API and frontend logs as a build artifact if anything fails
6. Generates the Allure report and deploys it to GitHub Pages, even when tests fail
