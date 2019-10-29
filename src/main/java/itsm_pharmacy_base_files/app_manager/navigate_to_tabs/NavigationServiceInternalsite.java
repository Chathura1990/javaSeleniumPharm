package itsm_pharmacy_base_files.app_manager.navigate_to_tabs;

import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import itsm_pharmacy_base_files.framework.mainClass.Parameters;
import org.openqa.selenium.WebDriver;

public class NavigationServiceInternalsite extends SelectorService {

    public NavigationServiceInternalsite(WebDriver driver){ super(driver); }

    public void goToInternalsiteMainPage(){ driver.get(Parameters.instance().getUrl() + "/app.php"); }

    public void goToFedexInventoryTracking(){
        driver.get(Parameters.instance().getUrl() + "/app.php/reports/fedex");
    }

    public void goToFedexShippedReport() { driver.get(Parameters.instance().getUrl()+ "/app.php/pharmacy/report/shipped_report"); }

    public void goToRxConfirmation(){ driver.get(Parameters.instance().getUrl() + "/app.php/tools/operations/batch/rx_confirm_status_change"); }
}
