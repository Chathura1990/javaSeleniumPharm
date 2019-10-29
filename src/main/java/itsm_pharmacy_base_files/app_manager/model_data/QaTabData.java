package itsm_pharmacy_base_files.app_manager.model_data;

public class QaTabData {

    private int rxid;
    private String durComment;

    public QaTabData rxid(int rxid){
        this.rxid = rxid;
        return this;
    }

    public QaTabData durComment(String durComment){
        this.durComment = durComment;
        return this;
    }

    public int getRxid(){
        return rxid;
    }

    public String getDurComment(){
        return durComment;
    }
}
