package itsm_pharmacy_base_files.app_manager.selector_helper;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.when;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class SelectorService {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public SelectorService(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * this method makes WebDriver poll the DOM for a certain amount of time when trying to locate an element.
     * @param units
     */
    public void implicit_Wait(int units) {
        driver.manage().timeouts().implicitlyWait(units, TimeUnit.SECONDS);
    }

    /**
     * this method will wait until completely load the page
     * @param units
     */
    public void pageLoad_Timeout(int units) {
        driver.manage().timeouts().pageLoadTimeout(units, TimeUnit.SECONDS);
    }

    public void randomWait() {
        try {
            sleep((long)(Math.random() * MILLISEC1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected WebElement visibilityOfElementLocatedBylocator(By locator, int unit) //Visibility Of Element Located By Xpath
    {
        wait = new WebDriverWait(driver, unit);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void invisibilityOfElementLocatedByLocator(By locator, int unit)//Invisibility Of Element Located By Xpath
    {
        wait = new WebDriverWait(driver, unit);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void actionSelectElementByLocator(By locator, String text, int unit)//Action Select Element By Xpath
    {
        Actions actions = new Actions(driver);
        actions.moveToElement(visibilityOfElementLocatedBylocator(locator, unit)).click().perform();
        reportLog("Clicked '" + text + "' using element: (" + locator + ")");
    }

    protected void scrollIntoViewByLocatorAndClick(By locator, String text) {
        WebElement locator1 = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locator1);
        locator1.click();
        reportLog("Clicked [" + text + "] using element: (" + locator + ")");
    }

    public void scrollUpOrDown(int value) throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+value+")");
        sleep(LONG_WAIT*2);
    }

    protected void ifElementIsDisplayedByLocator(By locator, By locator2, String text2) {
        if (driver.findElement(locator).isDisplayed()) {
            implicit_Wait(SECONDS20);
            reportLog("Opened " + text2);
            driver.findElement(locator2).click();
            reportLog("Closed " + text2);
        }
    }

    protected void waitElementToBeClickable(By locator) {
        wait = new WebDriverWait(driver, SECONDS20);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        reportLog("Wait element to be clickable: (" + locator + ")");
    }

    protected void click(By locator, String text) {
        visibilityOfElementLocatedBylocator(locator, SECONDS20);
        waitElementToBeClickable(locator);
        if (isELementPresent(locator)) {
            driver.findElement(locator).click();
        }
        reportLog("Clicked '" + text + "' using element: (" + locator + ")");
    }

    public void type(By locator, String text) {//Click on field,clear field and enter the text
        String inputFieldText =  visibilityOfElementLocatedBylocator(locator, SECONDS20).getText();
        driver.findElement(locator).click();
        if(inputFieldText != null) {
            driver.findElement(locator).clear();
        }
        driver.findElement(locator).sendKeys(text);
        reportLog("Typed: '" + text + "' in the field: (" + locator + ")");
    }

    protected void typeAndPressEnter(By locator, String text) {
        visibilityOfElementLocatedBylocator(locator, SECONDS20);
        driver.findElement(locator).click();
        if (text != null) {
            (new Actions(driver)).moveToElement(driver.findElement(locator))
                    .sendKeys(text)
                    .sendKeys(Keys.RETURN).perform();
            reportLog("Typed: '" + text + "' in the field: (" + locator + ") and pressed [Enter]");
        }
    }

    protected void hoverOnElement(By locator, By locator2, String text){
        WebElement element = driver.findElement(locator);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
        WebElement subElement = visibilityOfElementLocatedBylocator(locator2,10);
        reportLog("Wait for the Element "+locator);
        action.moveToElement(subElement);
        if(subElement.isDisplayed()) {
            action.perform();
            reportLog(text + " is appeared");
        }else{
            reportLog("Unable to locate " + text);
        }
    }

    public void closeNotification() throws InterruptedException {
        By notification = xpath("//div[@class='ui-pnotify-text']");
        By notificationCloseBtn = xpath("//span[@class='glyphicon glyphicon-remove']");
        Actions a = new Actions(driver);
        driver.findElements(notification).forEach(i -> a.moveToElement(i).build().perform());
        sleep(MILLISEC1000);
        driver.findElements(notificationCloseBtn).forEach(g -> a.moveToElement(g).click().build().perform());
        reportLog("Closed all the notifications");
    }

    public void minimizeHistoryWidget(){
        By historyMinimizeBtn = id("footprints-toggle");
        click(historyMinimizeBtn,"[glyphicon-minus] button");
    }

    protected void attach(By locator, File file) {
        if (file != null) {
            driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    protected String getText(By locator) {
        implicit_Wait(SECONDS20);
        return visibilityOfElementLocatedBylocator(locator, SECONDS20).getText();
    }

    public void getSuccessMessageCommon(){
        implicit_Wait(SECONDS20);
        if(driver.findElements(xpath("//div[@class='ui-pnotify-text']")).size()>0) {
            reportLog("Success Message: '" + getText(xpath("//div[@class='ui-pnotify-text']")));
        }else{
            reportLog("*** ELEMENT NOT FOUND ***");
        }
    }

    public int randomNumb(int min, int max){
        return (int)(Math.random()*((max-min)+1))+min;
    }

    protected String getAttribute(By locator, String value) {
        return driver.findElement(locator).getAttribute(value);
    }

    protected void selectAnOptionFromDropdown(By locator, int option){
        Select select = new Select(driver.findElement(locator));
        List<WebElement> options = select.getOptions();
        options.get(option).click();
    }

    public String getWebsiteTitle() {
        return driver.getTitle();
    }

    protected boolean isELementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    protected void clearField(By locator) {
        visibilityOfElementLocatedBylocator(locator, SECONDS20);
        driver.findElement(locator).click();
        (new Actions(driver)).moveToElement(driver.findElement(locator))
                .sendKeys(Keys.chord(Keys.CONTROL + "a"))
                .sendKeys(Keys.BACK_SPACE)
                .build().perform();
    }

    protected boolean checkDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/DD/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            reportLog("Please Correct the Date Format. "+ pe);
            return false;
        }
        reportLog("Date is in valid format");
        return true;
    }

    public boolean checkDateIsFutureDate(String date, String text) throws ParseException {
        Date OderDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
        //get current date
        Date current = new Date();
        reportLog("Get Current Date: " + current);
        reportLog("Get " + text + " Date: " + OderDate);
        //compare both dates
        if (OderDate.after(current)) {
            reportLog("The " + text + " Date is future day");
            return false;
        } else {
            reportLog("The " + text + " date is older than current");
            return true;
        }
    }

    public boolean checkImageIsAvailableOrNot(By locator1, String text1, String text2) {
        WebElement image = visibilityOfElementLocatedBylocator(locator1, SECONDS20);
        reportLog("wait for the element (" + text1 + ")");

        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && " + "typeof arguments[0].naturalWidth != \"undefined\" && " + "arguments[0].naturalWidth > 0", image);

        boolean loaded = false;
        if (result instanceof Boolean) {
            loaded = (Boolean) result;
            reportLog("'" + text2 + "' image is available -->" + " " + loaded);
        }
        return loaded;
    }

    public void assertSuccessMessage(String expectedMsg) {
        String SuccessMsg = visibilityOfElementLocatedBylocator(xpath("//div[@class='ui-pnotify-text']"), SECONDS20).getText();
        reportLog("Expected Message --> " + expectedMsg);
        reportLog("Actual Message--> " + SuccessMsg);
        Assert.assertEquals(SuccessMsg, expectedMsg);
    }

    protected int calculateAge(By locator) {
        String dob = driver.findElement(locator).getAttribute("value");
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date current = new Date();
        Date birthdate = null;
        try {
            birthdate = myFormat.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = current.getTime() / 1000 - Objects.requireNonNull(birthdate).getTime() / 1000;
        return Math.round(time) / 31536000;
    }

    protected void assertNumberOfOptionsInDropdown(By locator, String text, int realAmount){
        reportLog("**** Check "+text+" dropdown in Single Rx *******");
        List<WebElement> optionList = driver.findElement(locator).findElements(By.tagName("option"));
        WebElement element1 = driver.findElement(locator);
        Select select = new Select(element1);
        int numberOfOptions = select.getOptions().size();
        reportLog("Number of "+text+" --> "+ numberOfOptions);
        int j=1;
        for(WebElement option : optionList){
            reportLog(text+" -->"+" "+ option.getText());
            j++;
        }
        Assert.assertEquals(numberOfOptions,realAmount);
    }

    protected void checkTheLinkStatus(){
        wait = new WebDriverWait(driver, MILLISEC1000);
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        when().get(driver.getCurrentUrl()).then().statusCode(200);
        log.info("Page is completely loaded: " + ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void checkAnchorTagLinkStatus(By locator, String value) throws IOException {
        WebElement link = driver.findElement(locator);
        HttpURLConnection connection = (HttpURLConnection) new URL(link.getAttribute(value)).openConnection();
        connection.setConnectTimeout(LONG_WAIT);
        connection.setInstanceFollowRedirects( false );
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();

        if(code == 200){
            reportLog("Link: "+link+" code status is "+code);
        }else{
            reportLog("This link is corrupted: "+ link);
        }
    }
}
