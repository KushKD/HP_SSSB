package Model;

import java.io.Serializable;

/**
 * Created by kuush on 5/24/2016.
 */
public class AdsPOJO implements Serializable {

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getShortnotification() {
        return shortnotification;
    }

    public void setShortnotification(String shortnotification) {
        this.shortnotification = shortnotification;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    private String shortnotification;
    private String detailed;
    private String publish;
}
