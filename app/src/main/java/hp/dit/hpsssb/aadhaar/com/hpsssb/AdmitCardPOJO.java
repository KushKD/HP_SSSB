package hp.dit.hpsssb.aadhaar.com.hpsssb;

import java.io.Serializable;

/**
 * Created by kuush on 5/3/2016.
 */
public class AdmitCardPOJO implements Serializable {

    private String PostName;
    private String Name;
    private String FathersName;

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getReportingTime() {
        return ReportingTime;
    }

    public void setReportingTime(String reportingTime) {
        ReportingTime = reportingTime;
    }

    public String getDateofExamination() {
        return DateofExamination;
    }

    public void setDateofExamination(String dateofExamination) {
        DateofExamination = dateofExamination;
    }

    public String getCenterAddress() {
        return CenterAddress;
    }

    public void setCenterAddress(String centerAddress) {
        CenterAddress = centerAddress;
    }

    public String getExamCenter() {
        return ExamCenter;
    }

    public void setExamCenter(String examCenter) {
        ExamCenter = examCenter;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public byte[] getSignature() {
        return Signature;
    }

    public void setSignature(byte[] signature) {
        Signature = signature;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public String getFathersName() {
        return FathersName;
    }

    public void setFathersName(String fathersName) {
        FathersName = fathersName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }

    private byte[] Photo;
    private byte[] Signature;
    private String Address;
    private String District;
    private String RollNo;
    private String ExamCenter;
    private String CenterAddress;
    private String DateofExamination;
    private String ReportingTime;
    private String Duration;


}
