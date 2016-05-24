package Model;

import java.io.Serializable;

/**
 * Created by kuush on 5/2/2016.
 */
public class DashboardFormsPOJO implements Serializable {

    private String Application_Recived;
    private String HDFC;
    private String LMK;
    private String Offline;
    private String PNB;
    private String Total_Payment_Received;

    public String getTotal_Payment_Received() {
        return Total_Payment_Received;
    }

    public void setTotal_Payment_Received(String total_Payment_Received) {
        Total_Payment_Received = total_Payment_Received;
    }

    public String getPNB() {
        return PNB;
    }

    public void setPNB(String PNB) {
        this.PNB = PNB;
    }

    public String getOffline() {
        return Offline;
    }

    public void setOffline(String offline) {
        Offline = offline;
    }

    public String getLMK() {
        return LMK;
    }

    public void setLMK(String LMK) {
        this.LMK = LMK;
    }

    public String getHDFC() {
        return HDFC;
    }

    public void setHDFC(String HDFC) {
        this.HDFC = HDFC;
    }

    public String getApplication_Recived() {
        return Application_Recived;
    }

    public void setApplication_Recived(String application_Recived) {
        Application_Recived = application_Recived;
    }



}
