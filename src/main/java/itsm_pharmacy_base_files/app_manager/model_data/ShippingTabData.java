package itsm_pharmacy_base_files.app_manager.model_data;

public class ShippingTabData {

    private int rxid;

    public ShippingTabData rxid(int rxid){
        this.rxid = rxid;
        return this;
    }

    public int getRxid(){
        return rxid;
    }
}
