package Model;

import java.io.Serializable;

/**
 * Created by kuush on 1/6/2017.
 */

public class ExamScheduleResult implements Serializable {

    public String EndRollNo;
    public String ExamCentre;
    public String ExamDate;
    public String PostName;
    public String StartRollNo;

    public String getStartRollNo() {
        return StartRollNo;
    }

    public void setStartRollNo(String startRollNo) {
        StartRollNo = startRollNo;
    }

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }

    public String getExamDate() {
        return ExamDate;
    }

    public void setExamDate(String examDate) {
        ExamDate = examDate;
    }

    public String getExamCentre() {
        return ExamCentre;
    }

    public void setExamCentre(String examCentre) {
        ExamCentre = examCentre;
    }

    public String getEndRollNo() {
        return EndRollNo;
    }

    public void setEndRollNo(String endRollNo) {
        EndRollNo = endRollNo;
    }


}
