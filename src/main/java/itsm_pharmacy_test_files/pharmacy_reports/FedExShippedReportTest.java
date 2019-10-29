package itsm_pharmacy_test_files.pharmacy_reports;

import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static org.testng.Assert.assertFalse;

public class FedExShippedReportTest extends TestBase {

    /**
     * These test cases will check whether the 'FedEx shipping report' is available to download or not
     * and also will check the content of the report.
     */
    private List<Object[]> getTestDataFromExcelDocument(File path){
        return app.getFedexShippedReport().getDataFromExcel(path);
    }

    @DataProvider
    private Object[] getTestDataFromJsonFile() throws IOException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("./src/main/resources/testData.json"));
        JSONObject jsonObject = (JSONObject) obj;
        return new PatientsTabData[]{new PatientsTabData()
                .trackingNum(String.valueOf(jsonObject.get("TrackingNumber")))};
    }

    @Test(dataProvider="getTestDataFromJsonFile")
    public void assertTrackingIdAndPatIdUsingFedexShippedReport(PatientsTabData pat) throws ParseException, InterruptedException, IOException, AWTException {
        app.getNavigationServiceInternalsite().goToFedexShippedReport();
        app.getFedexShippedReport().checkDateAndClickDownloadXlsFile();
        File filePath = new File("./Report/STM_fedex_shipped_report_"
                + app.getFedexShippedReport().getFilterDatesFromTheShippedReport() + ".xlsx");
        System.out.println(filePath);
        List<Object[]> excelFileData = getTestDataFromExcelDocument(filePath);
        List<String> trackingNumbs = new ArrayList<>();
        for (Object[] excelFileDatum : excelFileData) {
            String data = Arrays.toString(excelFileDatum).replaceAll("\\D+", "");
            if (data.contains(pat.getTrackingNum()) && data.equals(pat.getTrackingNum())) {
                trackingNumbs.add(data);
            }
        }
        if(trackingNumbs.size()>0){
            reportLog("tracking# from .xlsx file " + trackingNumbs);
            reportLog("tracking# from .json file " + pat.getTrackingNum());
        }else{
            assertFalse(true,"There is no tracking number");
        }
        try{
            filePath.delete();
        }catch (Exception e){
            reportLog("File not Found");
            reportLog(e.getMessage());
        }
    }


}
