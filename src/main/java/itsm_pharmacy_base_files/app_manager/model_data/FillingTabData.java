package itsm_pharmacy_base_files.app_manager.model_data;

public class FillingTabData {

    private int rxid;

    public FillingTabData rxid(int rxid){
        this.rxid = rxid;
        return this;
    }

    public int getRxid(){
        return rxid;
    }
}
