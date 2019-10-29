package itsm_pharmacy_base_files.app_manager.pharmacy_reports_helper;

import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.LONG_WAIT;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.name;

public class FedexShippedReport extends SelectorService {

    private Xls_Reader reader;

    private By dateFrom = name("date_from");
    private By dateTo = name("date_to");
    private By downloadXlsxBtn = id("fedex-total-shipped-report");

    public FedexShippedReport(WebDriver driver) {
        super(driver);
    }

    private String clickDownloadXlsButton(){
        click(downloadXlsxBtn,"[Download xlsx] button");
        return driver.getWindowHandle();
    }

    public String getFilterDatesFromTheShippedReport(){
        String dateFromValue = getAttribute(dateFrom,"value").replaceAll("\\D","");
        String dateToValue = getAttribute(dateTo,"value").replaceAll("\\D", "");
        return (dateFromValue + "_" + dateToValue);
    }

    public void checkDateAndClickDownloadXlsFile() throws InterruptedException, ParseException, AWTException {
        String DATEFROM = getAttribute(dateFrom,"value");
        String DATETO = getAttribute(dateTo,"value");
        if(checkDateIsFutureDate(DATEFROM,"Shipped date from") && checkDateIsFutureDate(DATETO, "shipped date to")) {
            String originalHandle = clickDownloadXlsButton();
            sleep(LONG_WAIT + 2000);
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalHandle)) {
                    reportLog("Opened new window and Wait until download the Report");
                    driver.switchTo().window(handle);
                    driver.switchTo().alert();
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_ENTER);
                    driver.close();
                    reportLog("Closed new tab and go back to old window");
                }
            }
            driver.switchTo().window(originalHandle);
        }
    }

    public ArrayList<Object[]> getDataFromExcel(File path){
        ArrayList<Object[]> trackingData = new ArrayList<>();
        try{
            reader = new Xls_Reader(path);
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int rowNum = 2; rowNum <= reader.getRowCount("Worksheet"); rowNum++){
            PatientsTabData[] ob = {new PatientsTabData().trackingNum(
                    (reader.getCellData("Worksheet", "Tracking#", rowNum).replaceAll("\\D", "")))};
            trackingData.add(ob);
        }
        return trackingData;
    }
    
}
