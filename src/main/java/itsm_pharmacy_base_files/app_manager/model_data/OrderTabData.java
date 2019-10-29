package itsm_pharmacy_base_files.app_manager.model_data;

public class OrderTabData {

    private int orderId;
    private int dispenseNDC;

    public OrderTabData OrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderTabData dispenseNDC(int dispenseNDC){
        this.dispenseNDC = dispenseNDC;
        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public int  getDispenseNDC(){
        return dispenseNDC;
    }
}
