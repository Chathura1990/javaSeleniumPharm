package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.QaTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.*;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class QaTabService extends SelectorService {

    private By QaSearchInputFeild = id("rx_qa_id");
    private By CloseBtn = xpath("//*[@id='_Modal_0']/div/div/div[2]/div[4]/div/button[2]");
    private By ThiredBlockForm = xpath("//div[@class='white-panel-wrapper'][3]");
    private By ApproveBtn = xpath("//button[@class='btn btn-success btn-xs approve ']");
    private By DUROverrideReason = id("qa_confirm_interactions_comment");
    private By DURApprovebtn = xpath("//*[@id='_Modal_0']/div/div/div[3]/button[1]");

    public QaTabService(WebDriver driver){
        super(driver);
    }

    public void enterRxIdAndPressEnter(QaTabData qaTabData) throws InterruptedException {
        WebElement qaSearch = driver.findElement(QaSearchInputFeild);
        qaSearch.sendKeys(String.valueOf(qaTabData.getRxid()));
        reportLog("Entered Rx Id into field [Scan Leaflet] Searchbox");
        sleep(MILLISEC2000);
        qaSearch.sendKeys(Keys.RETURN);//Sometimes by pressing [Enter] key, QaTab details will not appear. Therefore need to press it twice.
        qaSearch.sendKeys(Keys.RETURN);
        reportLog("Clicked [Enter] button");
    }

    public void clickOrderApproveBtn() throws InterruptedException {
        invisibilityOfElementLocatedByLocator(CloseBtn, SECONDS20 -10);
        reportLog("Wait until invisibility of 'Drug Interactions' Widget");
        driver.findElement(ThiredBlockForm).click();
        sleep(LONG_WAIT);
        click(ApproveBtn,"[Approve] button");
    }

    public void enterCommentForDur(QaTabData qaTabData){
        type(DUROverrideReason,qaTabData.getDurComment());
    }

    public void clickDurApproveBtn(){
        click(DURApprovebtn,"Dur [Approve] button");
    }


}
