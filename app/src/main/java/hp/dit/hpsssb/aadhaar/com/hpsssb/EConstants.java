package hp.dit.hpsssb.aadhaar.com.hpsssb;

/**
 * Created by kuush on 5/10/2016.
 */
public class EConstants {

    public static final String url_Generic = "" ;
    public static final String function_Instructions = "" ;
    public static final String function_Vacancies = "" ;
    public static final String function_GetOTP = "";
    public static final String function_VerifyOTP = "";
    public static final String function_DashboardCReport = "";
    public static final String function_Dashboard = "";
    public static final String function_getAdmitCardPersonalDetails = "";
    public static final String function_getAdmitCardAadhaar = "";
    public static final String HTTP_Verb_Get = "";
    public static final String HTTP_Verb_post = "";
    public static final int Connection_TimeOut = 0000;
    public static final String UNICODE = "utf-8";
    public static final String InstructionsResult = "";
    public static final String Vacancies_Result = "";
    public static final String OTP_Result = "";
    public static final String Login_Result="";
    public static final String DashboardCReport_Result="";
    public static final String Dashboard_Result = "";
    public static final String AdmitCardAadhaar_Result = "";
    public static final String AdmitCardPersonalDetails_Result = "";
    public static final String Delemeter = "";
    public static final String progress_Dialog_Message = "Please be patient,as the application is trying to connect to the Internet";
    public static final String Error_NoIdea = "Something went wrong, while fetching the results. Please try again later.";
    public static final String Error_NoNetwork = "Unable to connect to Internet. Please check your network connection and try again.";
    public static final String Path_PDF = "/sdcard/HPSSSB/.pdf";
    public static final String Intent_Type = "application/pdf";
    public static final int Chuck_Size = 1024;
    public static final String Error_NoPDF_Viewer = "No Application Available to View PDF";
    public static final String Error_DownloadFile = "The downloaded file is not a valid format.";
    public static final String Messages_Results = "No pending results in pipeline.";
    public static final String Messages_Interview="Currently there is no Interview Scheduled.";
    public static final String EnycType="";
    public static final String EnycError = "String to encript cannot be null or zero length";

    /**
     * HPSSSB Vacancies Page
     */
    public static final String Messages_Vacancis="There are no current vacancies.";
    public static final String PutExtra_Message_Vacancies = "";
    public static final String PutExtra_Message_Vacancies_Date ="";
    public static final String ApplyButton_AlertMessage ="You'll be redirected to the http://hpsssb.hp.gov.in. Do you want to continue? Press Proceed to continue and Quit to exit.";
    public static final String WebSite_Link = "";

    /**
     * Login Screen
     */
    public static final String ErrorMobile = "Please enter a valid mobile number.";
    public static final String Messages_OTP = "Please wait , an OTP will be sent to this mobile number";
    public static final String Date_Format = "MM-dd-yyyy";
    public static final String Error_ValidDates = "Please input valid dates";
    public static final String  Put_From_Date="DATE_TO_SEND_FROM";
    public static final String  Put_To_Date="DATE_TO_SEND_TO";

    /**
     * Admit Card
     */
    public static final String Put_Aadhaar  ="Aadhaar_Service";
    public static final String Put_Name  ="Name_Service";
    public static final String Put_DOB  ="DOB_Service";
    public static final String Put_ApplicationID  ="ApplicationID_Service";
    public static final String Error_Aadhaar = "Please enter either your valid Aadhaar number or complete personal Details.";
}
