package itsm_pharmacy_base_files.app_manager.navigate_to_tabs;

import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static org.openqa.selenium.By.*;

public class NavigationServiceQueuesTabs extends SelectorService {

    private By PendingRxesQueue = xpath("//span[contains(text(),'Pending RXes')]/parent::a");
    private By RxCounter = cssSelector("[class='pointer']");
    private By TotalPendingOrders = xpath("//ul[@id='pharmacy_tabs']/li[9]/ul/li[2]/a");
    private By OrderCounter = cssSelector("[class='pointer']");
    private By ToVerifyBtn = xpath("//*[@id='rx-queues-result-container']/div/ul/ul[3]/li[1]/a/span");
    private By Returns = xpath("//*[@id='rx-queues-result-container']/div/ul/ul[10]/li[1]/a/span");

    public NavigationServiceQueuesTabs(WebDriver driver) {
        super(driver);
    }

    public void goToPendingRxes() {
        click(PendingRxesQueue, "[Pending Rxes] queue");
    }

    public void selectValidRxFromQueues() {
        if (isELementPresent(RxCounter)) {
        List<WebElement> AmountOfPendingRxes = driver.findElements(RxCounter);
        int countRows = AmountOfPendingRxes.size();
        reportLog("Amount of available Rxes in (Pending Rxes/No Allergy Info):" + " " + countRows);
        reportLog("Check if there are Rxes submitted by the user " + VALID_USERNAME + " to prevent getting old Rxes");
        int i = 1;
        do {
            WebElement usermodified = driver.findElement(By.xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[7]"));
            String modified = usermodified.getText();
            if (modified.equals(VALID_USERNAME)) {
                driver.findElement(By.xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[2]/a/span")).click();
                reportLog("Chose Rx by User Last Modified: " + VALID_USERNAME);
                break;
            }
            i++;
        } while (i <= AmountOfPendingRxes.size());
        }
    }

    public void goToTotalPendingOrders() {
        click(TotalPendingOrders, "[Total Pending Orders] queue");
    }

    public void selectNewOrderFromQueues() {
        if (isELementPresent(OrderCounter)) {
            List<WebElement> AmountOfPendingOrders = driver.findElements(OrderCounter);
            int countRows = AmountOfPendingOrders.size();
            reportLog("Amount of available Orders in (Processed Today / Total Pending Orders):" + " " + countRows);
            int i=1;

            do{
                implicit_Wait(SECONDS20);
                WebElement rfStatus = driver.findElement(xpath("//*[@id='rx-queues-result-container']/table/tbody/tr["+i+"]/td[4]"));
                String refillStatus = rfStatus.getText();

                if(refillStatus.equals("New")|refillStatus.equals("Refill")) {
                    driver.findElement(xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[2]/a/span")).click();
                    reportLog("Chose order by: Refill Status = ["+refillStatus+"]");
                    break;
                }i++;

            }while (i<=AmountOfPendingOrders.size());
        }
    }

    public void goToVerify() {
        click(ToVerifyBtn, "[Verify Orders] queue");
    }

    public void selectSingleTemplateOrderFromQueues(){
        if(isELementPresent(OrderCounter)){
            List<WebElement> AmountOfTemplates = driver.findElements(OrderCounter);
            int countRows = AmountOfTemplates.size();
            reportLog("Amount of available Order Templates in (To verify): " + countRows);
            reportLog("Check if there are Single Order Templates by: Type = 'Single' to prevent getting 'Bundle' order templates");
            int i = 1;
            do {
                implicit_Wait(SECONDS20);
                WebElement tempType = driver.findElement(By.xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[6]"));
                String templateType = tempType.getText();

                if (templateType.equals("Single")) {
                    driver.findElement(By.xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[2]/a/span")).click();
                    reportLog("Chose Template Type by: Type = ["+templateType+"]");
                    break;
                }
                i++;
            } while (i < AmountOfTemplates.size());
        }
    }

    public void goToReturnsQueue(){
        click(Returns, "[Returns Order] queue");
    }

    public void selectReturnOrderFromQueues() {
        if(isELementPresent(OrderCounter)){
            List<WebElement> AmountOfReturnedOrders = driver.findElements(OrderCounter);
            reportLog("Amount of available returned orders in 'Returns' queue:"+ AmountOfReturnedOrders.size());
            reportLog("Check if there are Template type: 'Bundle' and Rx Condition: 'In progress' orders or not.");
            for(int i = 1; AmountOfReturnedOrders.size()>i; i++){
                implicit_Wait(SECONDS20);
                String templateType = driver.findElement(xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[7]")).getText();
                String rxCondition = driver.findElement(xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[8]")).getText();

                if(templateType.equals("Bundle") && rxCondition.equals("In progress")){
                    click(xpath("//*[@id='rx-queues-result-container']/table/tbody/tr[" + i + "]/td[6]/button[1]")
                            ,"[Go to Return info] button");
                    reportLog("Selected No."+i+" Return Order by Template type: "+templateType+" & Rx condition: "+rxCondition);
                }
            }
        }
    }

}
