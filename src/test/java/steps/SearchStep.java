package steps;

import static org.testng.Assert.assertEquals;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class SearchStep {

  private final By BUTTON_SUBMIT = By.xpath("//button[@type='submit']");
  private final String BOOKING_URL = "https://www.booking.com/searchresults.en-gb.html";
  private final By FIELD_INPUT_NAME_HOTEL = By.xpath("//input[@name='ss']");
  private final By TITLE_HOTEL = By.xpath("//div[@data-testid='title']");
  private final By RATING_HOTEL = By.cssSelector("[data-testid='review-score'] > div:first-child + div");

  WebDriver driver;

  @Before
  public void setup() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
  }

  @Given("booking search page is opened")
  public void bookingSearchPageIsOpened() throws InterruptedException {
    driver.get(BOOKING_URL);
    Thread.sleep(4000);
  }

  @When("user searches for {string}")
  public void userSearchesFor(String hotel) throws InterruptedException {
    driver.findElement(FIELD_INPUT_NAME_HOTEL).sendKeys(hotel);
    driver.findElement(
        By.xpath(String.format("//ul[@role='group']//*[contains(text(), '%s')]", hotel))).click();
    driver.findElement(BUTTON_SUBMIT).click();
    Thread.sleep(4000);
  }

  @Then("{string} is shown")
  public void hotelIsShown(String expectedResult) {
    List<WebElement> titles = driver.findElements(TITLE_HOTEL);
    boolean isHotelFound = false;
    for (WebElement title : titles) {
      if (title.getText().equals(expectedResult)) {
        isHotelFound = true;
        break;
      }
    }
    Assert.assertTrue(isHotelFound);
  }

  @After
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Then("{string} hotel is shown")
  public void hotelIsShow(String arg0) {
  }

  @And("hotel has rating {string}")
  public void hotelHasRating(String rate) {
    WebElement ratingElement = driver.findElement(RATING_HOTEL);
    String actualRating = ratingElement.getText();
    assertEquals(actualRating, rate, "Рейтинг должен быть " + rate);
  }
}

