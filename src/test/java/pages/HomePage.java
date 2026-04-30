package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By feedCardLocator  = By.cssSelector(".news-card");
    private final By cardTitleLocator = By.cssSelector(".news-card h3");
    private final By cardLinkLocator  = By.cssSelector("a.news-card");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    public List<WebElement> getFeedCards() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(feedCardLocator));
        return driver.findElements(feedCardLocator);
    }
    public List<WebElement> getCardTitles() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(feedCardLocator));
        return driver.findElements(cardTitleLocator);
    }
    public List<WebElement> getCardLinks() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(feedCardLocator));
        return driver.findElements(cardLinkLocator);
    }
    public String getPageTitle() { return driver.getTitle(); }
}
