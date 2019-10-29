package itsm_pharmacy_test_files.general_tests;

import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;

public class AllTheValidLinksInPharmacyTest extends TestBase {

    @Priority(1)
    @Test(priority = 1)
    public void get_Website_Title_Test() {
        reportLog("********** Get pharmacy website title **************");
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        String title = app.getSelectorService().getWebsiteTitle();
        reportLog("Sites Expected Title-->" + " " + "Pharmacy | Test (Rx) Internal Site (STG environment)");
        reportLog("Site Actual Title-->" + " " + title);
        Assert.assertEquals(title, "Pharmacy | Test (Rx) Internal Site (STG environment)");
        reportLog("********** End Test Case 2 **********");
    }

    @Priority(2)
    @Test(priority = 2, description = "Get the list of all the valid links,images,inputs and buttons from pharmacy page")
    public void search_All_The_Valid_Links_In_Pharmacy_Test() throws IOException {
        log.info("");
        reportLog("********** Started Checking valid links **********");
        app.getNavigationServicePharmMain().goToPharmacyMainPage();

        //1.Get the list of all the links,images,inputs and buttons.
        List<WebElement> AllTheLinkList = driver.findElements(By.tagName("a"));
        AllTheLinkList.addAll(driver.findElements(By.tagName("img")));

        reportLog("Total amount of full links ----> " + AllTheLinkList.size());

        List<WebElement> activeLinks = new ArrayList<WebElement>();

        //2.Iterate LinksList: Exclude all the links/images - doesn't have any href attribute and exclude images starting with javascript.
        boolean breakIt;
        for (WebElement link : AllTheLinkList) {
            breakIt = true;
            try {
                if (link.getAttribute("href") != null && !link.getAttribute("href").contains("javascript") && link.getAttribute("href").contains("pharmacy")
                         && !link.getAttribute("href").endsWith("pharmacy/main#") && !link.getAttribute("href").matches(".*/\\d+$")) {
                    activeLinks.add(link);
                }
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                breakIt = false;
            }
            if (breakIt) {
                continue;
            }
        }

        //Get total amount of Other links
        reportLog("Other Links ---> " + (AllTheLinkList.size() - activeLinks.size()));
        //Get total amount of links in the page
        reportLog("Size of active links and images in pharmacy ---> " + activeLinks.size());


        //3.Check the href url, with httpconnection api:
        //200 -- Ok
        //302 -- found
        //400 -- bad request
        //403 -- Forbidden
        //404 -- not found
        //500 -- internal error

        for (int j = 0; j < activeLinks.size(); j++)
        {
            HttpURLConnection connection = (HttpURLConnection) new URL(activeLinks.get(j).getAttribute("href")).openConnection();

            connection.setConnectTimeout(LONG_WAIT);
            connection.setInstanceFollowRedirects( false );
            connection.connect();
            String response = connection.getResponseMessage(); //Ok
            int code = connection.getResponseCode();
            connection.disconnect();


            if (code == 400 | code == 403 | code == 404 | code == 500){
               reportLog((j + 1) + "/" + activeLinks.size()+ " " + "Corrupted link" + ":--->" + activeLinks.get(j).getAttribute("href") + " " +"-- Code:"+ code);
           }else{
                reportLog((j + 1) + "/" + activeLinks.size() + " " + activeLinks.get(j).getAttribute("href") + " " + "---> Status:" + response + " ----> Code:" + code);
            }

        }
        reportLog("**************** End Test Case 3 *****************");
        reportLog("********** THE END :Pharmacy Test logs: **********");
    }
}
