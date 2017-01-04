package Utils;

/**
 * Created by kuush on 5/10/2016.
 */
public class EConstants {

   // public static final String url_Generic = "http://hpsssb.hp.gov.in/hpsssbwebAPI/HPSSSB_REST.svc" ; // Main Server
   public static final String url_Generic = "http://192.168.1.103/hpssbapi/HPSSSB_REST.svc"; //JIO Amit
    public static final String function_Instructions = "getInstructions_JSON" ;
    public static final String function_Vacancies = "getVacancies_JSON" ;
    public static final String function_GetOTP = "getLogin_JSON";
    public static final String function_VerifyOTP = "getLoginAuth_JSON";
    public static final String function_DashboardCReport = "getDashboardCReport_JSON";
    public static final String function_Dashboard = "getDashboard_JSON";
    public static final String function_getAdmitCardPersonalDetails = "getAdmitCardPersonalDetails_JSON";
    public static final String function_getAdmitCardAadhaar = "getAdmitCardAadhaar_JSON";
    public static final String HTTP_Verb_Get = "GET";
    public static final String HTTP_Verb_post = "POST";
    public static final int Connection_TimeOut = 20000;
    public static final String UNICODE = "utf-8";
    public static final String InstructionsResult = "JSON_getInstructionsResult";
    public static final String Vacancies_Result = "JSON_VacanciesResult";
    public static final String OTP_Result = "JSON_LoginResult";
    public static final String Login_Result="JSON_LoginAuthResult";
    public static final String DashboardCReport_Result="JSON_DashboardCReportResult";
    public static final String Dashboard_Result = "JSON_DashboardResult";
    public static final String AdmitCardAadhaar_Result = "JSON_AdmitCardAadharResult";
    public static final String AdmitCardPersonalDetails_Result = "JSON_AdmitCardPersonalDetailsResult";
    public static final String Delemeter = "/";
    public static final String progress_Dialog_Message = "Please be patient,as the application is trying to connect to the Internet";
    public static final String Error_NoIdea = "Something went wrong, while fetching the results. Please try again later.";
    public static final String Error_NoNetwork = "Unable to connect to Internet. Please check your network connection and try again.";
    public static final String Path_PDF = "/sdcard/HPSSSB/HPSSSB_Instructions.pdf";
    public static final String Intent_Type = "application/pdf";
    public static final int Chuck_Size = 1024;
    public static final String Error_NoPDF_Viewer = "No Application Available to View PDF";
    public static final String Error_DownloadFile = "The downloaded file is not a valid format.";
    public static final String Messages_Results = "No pending results in pipeline.";
    public static final String Messages_Interview="Please select relevant option.";

    /**
     * HPSSSB Vacancies Page
     */
    public static final String Messages_Vacancis="There are no current vacancies.";
    public static final String PutExtra_Message_Vacancies = "Details";
    public static final String PutExtra_Message_Vacancies_Date ="DATE_TO_SEND";
    public static final String ApplyButton_AlertMessage ="You'll be redirected to the http://hpsssb.hp.gov.in. Do you want to continue? Press Proceed to continue and Quit to exit.";
    public static final String WebSite_Link = "http://hpsssb.hp.gov.in/vacancies.aspx";

    /**
     * Login Screen
     */
    public static final String ErrorMobile = "Please enter a valid mobile number.";
    public static final String Messages_OTP = "Please wait , an OTP will be sent to this mobile number";
    public static final String Date_Format = "dd-MM-yyyy";
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

    /**
     * Login Screen
     */
    public static final String PREFS_NAME  ="HPSSSB";
    public static final String function_Register  ="postRegistration_JSON";



}
