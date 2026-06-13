package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "http://localhost:5173";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // ── HEADLESS MODE FOR CI ──────────────────────────────
        // The CI workflow passes -Dheadless=true via the mvn command.
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            // "--headless=new" is the modern headless mode (Chrome 112+).
            // It renders pages closer to a real browser than the old
            // "--headless" flag, which matters for layout-sensitive tests.
            options.addArguments("--headless=new");

            // Without a real display, Chrome doesn't know a window size,
            // which can make responsive layouts render incorrectly.
            // Setting this explicitly keeps your test viewport consistent
            // between local runs and CI runs.
            options.addArguments("--window-size=1920,1080");
        }
        // ───────────────────────────────────────────────────────

        driver = new ChromeDriver(options);

    
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
