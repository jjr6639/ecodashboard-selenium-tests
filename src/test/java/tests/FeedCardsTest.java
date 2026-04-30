package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import java.util.List;

@Feature("RSS Feed Cards")
public class FeedCardsTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void initPage() {
        homePage = new HomePage(driver);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify at least one RSS feed card renders on the homepage")
    public void feedCardsArePresentTest() {
        List<WebElement> cards = homePage.getFeedCards();
        Assert.assertFalse(cards.isEmpty(), "No feed cards found");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify every feed card has a non-empty title")
    public void feedCardTitlesAreNotEmptyTest() {
        List<WebElement> titles = homePage.getCardTitles();
        Assert.assertFalse(titles.isEmpty(), "No card titles found");

        for (WebElement title : titles) {
            Assert.assertFalse(
                title.getText().trim().isEmpty(),
                "A feed card has an empty title"
            );
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify every feed card link has a valid href")
    public void feedCardLinksAreValidTest() {
        List<WebElement> links = homePage.getCardLinks();
        Assert.assertFalse(links.isEmpty(), "No card links found");

        for (WebElement link : links) {
            String href = link.getAttribute("href");
            Assert.assertNotNull(href, "A card link has no href");
            Assert.assertTrue(href.startsWith("http"), "Invalid URL: " + href);
        }
    }
}
