package hp.dit.hpsssb.aadhaar.com.hpsssb;

import java.io.Serializable;

/**
 * Created by kuush on 5/2/2016.
 */
public class DashboardPostPOJO implements Serializable {

    private String Post_Name;
    private String Female;
    private String Male;
    private String Others;

    public String getPost_Name() {
        return Post_Name;
    }

    public void setPost_Name(String post_Name) {
        Post_Name = post_Name;
    }

    public String getFemale() {
        return Female;
    }

    public void setFemale(String female) {
        Female = female;
    }

    public String getMale() {
        return Male;
    }

    public void setMale(String male) {
        Male = male;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }


}
