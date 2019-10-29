package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.ShippingTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static itsm_pharmacy_test_files.order_tab.OrderTabWorkflow.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class ShippingTabService extends SelectorService {

    private By RxIdInputField = id("rx_shipping_add_rx_form_type_rxId");
    private By SubmitBtn = id("rx_shipping_add_rx_form_type_add");
    private By SaveWeightBtn = xpath("//*[@class='save_weight_in_table btn btn-success']");
    private By SuccessMessage = xpath("//div[@class='ui-pnotify-text']");
    private By OrderHistory =  xpath("//button[@class='btn btn-primary glyphicon glyphicon-calendar show-history']");
    private By OrderStatus = xpath("//*[@id='_Modal_0']/div/div/div[2]/div/div/table/tbody/tr[4]/td[2]");
    private By OrderAction = xpath("//*[@id='_Modal_0']/div/div/div[2]/div/div/table/tbody/tr[4]/td[3]");
    private By CloseBtn = xpath("//*[@id='_Modal_0']/div/div/div[1]/button/span[1]");
    private By SaveOrderBtn = xpath("//button[@class='btn btn-primary glyphicon glyphicon-save save-order-btn']");
    private By ShippingMethd = id("rx_shipping_process_form_no_shipment_type_shipment_shippingMethod");
    private By SaveShippingMethodBtn = id("shipping_tab_change_shipment_method");
    private By FinalizeBtn = id("rx_shipping_process_form_no_shipment_type_save");

    public ShippingTabService(WebDriver driver){
        super(driver);
    }

    public void enterRxId(ShippingTabData shippingTabData){
        type(RxIdInputField, String.valueOf(shippingTabData.getRxid()));
    }

    public void clickSubmitBtn(){
        click(SubmitBtn,"[Submit] button");
    }

    public void clickSaveWeightBtn() throws InterruptedException {
        if(driver.findElement(SaveWeightBtn).isDisplayed()) {
            sleep(LONG_WAIT);
            click(SaveWeightBtn,"[Save Weight] button");
        }
    }

    public void getSuccessMessage(){
        reportLog("Success Message: " + visibilityOfElementLocatedBylocator(SuccessMessage, SECONDS20).getText());
    }

    public void clickOrderHistoryBtn(){
        click(OrderHistory,"[Order History] button");
    }

    public void getStatusOfTheOrder() throws InterruptedException {
        sleep(MILLISEC2000);
        if (visibilityOfElementLocatedBylocator(xpath("//*[contains(text(),'Order #"+OrderNumberById+" history')]"), SECONDS20).isDisplayed()
                && driver.findElement(OrderStatus).getText().equals("Approved")){
            reportLog("Order Status: "+driver.findElement(OrderStatus).getText()+" / "
                    +" Action: "+driver.findElement(OrderAction).getText());

            driver.findElement(CloseBtn).click();
        }
    }

    public void clickSaveOrderBtn(){
        click(SaveOrderBtn,"[Save Order] button");
    }

    public void selectShippingMethod(int index){
        implicit_Wait(SECONDS20);
        Select ShippingMethod = new Select(driver.findElement(ShippingMethd));
        List<WebElement> options = ShippingMethod.getOptions();
        options.get(index).click();
        reportLog("Selected shipping method: "+options.get(index).getText());
    }

    public void clickSaveShippingMethodBtn(){
        click(SaveShippingMethodBtn,"[Save Shipping Method] button");
    }

    public void clickFinalizedBtn(){
        click(FinalizeBtn,"[Finalized] button");
    }


}
