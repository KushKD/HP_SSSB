using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data.SqlClient;
using System.Data;
using WEB_API_HPSSSB.HelperClasses;
using System.Security.Cryptography;
using System.IO;
using System.Net;
using System.Web;
using System.Net.Mail;

namespace WEB_API_HPSSSB
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "HPSSSB_REST" in code, svc and config file together.
    public class HPSSSB_REST : IHPSSSB_REST
    {

        SqlConnection dbConnection;

        #region Basic Testing Functions Rest Architecture working

        public string XMLData(string id)
        {
            return id;
        }


        public string JSONData(string id)
        {
            return id;
        }

        #endregion  working
        #region get Instructions XML and JSON Link working


        public string XML_getInstructions()
        {
            try
            {
                return "http://hpsssb.hp.gov.in/Uploads/HPSSSB%20Instructions.pdf";
            }
            catch (Exception e)
            {
                return e.GetBaseException().ToString().Trim();
            }
        }


        public string JSON_getInstructions()
        {
            try
            {
                return "http://hpsssb.hp.gov.in/Uploads/HPSSSB%20Instructions.pdf";
            }
            catch (Exception e)
            {
                return e.GetBaseException().ToString().Trim();
            }
        }


        #endregion
        #region Get Vacancies XML and JSON working  send date as dd-mm-yyyy
        List<Vacancies> vacancy_List = null;
        public IEnumerable<Vacancies> XML_Vacancies(string date)
        {
            string new_date = date.Replace("-", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("GetAllCurrentVacancies", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Ldate", new_date);
                cmd.Parameters.AddWithValue("@PDate", new_date);

                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    vacancy_List = new List<Vacancies>();
                    Vacancies objVac = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objVac = new Vacancies();
                        objVac.SNO = dt.Tables[0].Rows[i]["SNO"].ToString();
                        objVac.PostID = dt.Tables[0].Rows[i]["PostId"].ToString();
                        objVac.Department = dt.Tables[0].Rows[i]["Department"].ToString();
                        objVac.PostName = dt.Tables[0].Rows[i]["PostName"].ToString();
                        objVac.PostCode = dt.Tables[0].Rows[i]["PostCode"].ToString();
                        objVac.Details = dt.Tables[0].Rows[i]["Details"].ToString();
                        objVac.PubDate = dt.Tables[0].Rows[i]["PubDate"].ToString();
                        objVac.LastDate = dt.Tables[0].Rows[i]["LastDate"].ToString();
                        vacancy_List.Add(objVac);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return vacancy_List;
        }
        public IEnumerable<Vacancies> JSON_Vacancies(string date)
        {
            string new_date = date.Replace("-", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("GetAllCurrentVacancies", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Ldate", new_date);
                cmd.Parameters.AddWithValue("@PDate", new_date);

                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    vacancy_List = new List<Vacancies>();
                    Vacancies objVac = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objVac = new Vacancies();
                        objVac.SNO = dt.Tables[0].Rows[i]["SNO"].ToString();
                        objVac.PostID = dt.Tables[0].Rows[i]["PostId"].ToString();
                        objVac.Department = dt.Tables[0].Rows[i]["Department"].ToString();
                        objVac.PostName = dt.Tables[0].Rows[i]["PostName"].ToString();
                        objVac.PostCode = dt.Tables[0].Rows[i]["PostCode"].ToString();
                        objVac.Details = dt.Tables[0].Rows[i]["Details"].ToString();
                        objVac.PubDate = dt.Tables[0].Rows[i]["PubDate"].ToString();
                        objVac.LastDate = dt.Tables[0].Rows[i]["LastDate"].ToString();
                        vacancy_List.Add(objVac);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return vacancy_List;
        }

        #endregion
        #region Dashboard Bank for XML and JSON Working
        List<DashboardBank> DashboardBank_List = null;
        public IEnumerable<DashboardBank> XML_DashBoard(string fromdate, string todate)
        {
            string new_fromdate = fromdate.Replace("-", "/");
            string new_todate = todate.Replace("-", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_DashBoard", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@FrmDate", Convert.ToDateTime(new_fromdate).ToString("yyyy/MM/dd"));
                cmd.Parameters.AddWithValue("@TDate", Convert.ToDateTime(new_todate).ToString("yyyy/MM/dd"));

                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                  adp.Fill(dt);
                 // Convert DataSet to List
                  DashboardBank_List = new List<DashboardBank>();
                  DashboardBank objDashBoard = null;
                   for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                  {
                    objDashBoard = new DashboardBank();
                    objDashBoard.Application_Recived = dt.Tables[0].Rows[i][0].ToString();
                    objDashBoard.Total_Payment_Received = dt.Tables[0].Rows[i][1].ToString();
                    objDashBoard.PNB = dt.Tables[0].Rows[i][2].ToString();
                    objDashBoard.HDFC = dt.Tables[0].Rows[i][3].ToString();
                    objDashBoard.LMK = dt.Tables[0].Rows[i][4].ToString();
                    objDashBoard.Offline = dt.Tables[0].Rows[i][5].ToString();

              DashboardBank_List.Add(objDashBoard);
              }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return DashboardBank_List;

        }
        public IEnumerable<DashboardBank> JSON_Dashboard(string fromdate, string todate)
        {
            string new_fromdate = fromdate.Replace(".", "/");
            string new_todate = todate.Replace(".", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_DashBoard", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@FrmDate", Convert.ToDateTime(new_fromdate).ToString("yyyy/MM/dd"));
                cmd.Parameters.AddWithValue("@TDate", Convert.ToDateTime(new_todate).ToString("yyyy/MM/dd"));

                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    DashboardBank_List = new List<DashboardBank>();
                    DashboardBank objDashBoard = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objDashBoard = new DashboardBank();
                        objDashBoard.Application_Recived = dt.Tables[0].Rows[i][0].ToString();
                        objDashBoard.Total_Payment_Received = dt.Tables[0].Rows[i][1].ToString();
                        objDashBoard.PNB = dt.Tables[0].Rows[i][2].ToString();
                        objDashBoard.HDFC = dt.Tables[0].Rows[i][3].ToString();
                        objDashBoard.LMK = dt.Tables[0].Rows[i][4].ToString();
                        objDashBoard.Offline = dt.Tables[0].Rows[i][5].ToString();

                        DashboardBank_List.Add(objDashBoard);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return DashboardBank_List;
        }


        #endregion
        #region Dashboard Consolodated Report XML and JSON
        List<DashboardCReport> DashboardCReport_List = null;
        public IEnumerable<DashboardCReport> XML_DashBoardCReport(string fromdate, string todate)
        {
            string new_fromdate = fromdate.Replace("-", "/");
            string new_todate = todate.Replace("-", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_GetConsolidatedReport", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@FrmDate", Convert.ToDateTime(new_fromdate).ToString("yyyy/MM/dd"));
                cmd.Parameters.AddWithValue("@TDate", Convert.ToDateTime(new_todate).ToString("yyyy/MM/dd"));

                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    DashboardCReport_List = new List<DashboardCReport>();
                    DashboardCReport objDashBoardCReport = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objDashBoardCReport = new DashboardCReport();
                        objDashBoardCReport.Post_Name = dt.Tables[0].Rows[i][0].ToString();
                        objDashBoardCReport.Male = dt.Tables[0].Rows[i][1].ToString();
                        objDashBoardCReport.Female = dt.Tables[0].Rows[i][2].ToString();
                        objDashBoardCReport.Others = dt.Tables[0].Rows[i][3].ToString();

                        DashboardCReport_List.Add(objDashBoardCReport);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return DashboardCReport_List;
        }

        public IEnumerable<DashboardCReport> JSON_DashboardCReport(string fromdate, string todate)
        {
            string new_fromdate = fromdate.Replace("-", "/");
            string new_todate = todate.Replace("-", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_GetConsolidatedReport", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@FrmDate", Convert.ToDateTime(new_fromdate).ToString("yyyy/MM/dd"));
                cmd.Parameters.AddWithValue("@TDate", Convert.ToDateTime(new_todate).ToString("yyyy/MM/dd"));

                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    DashboardCReport_List = new List<DashboardCReport>();
                    DashboardCReport objDashBoardCReport = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objDashBoardCReport = new DashboardCReport();
                        objDashBoardCReport.Post_Name = dt.Tables[0].Rows[i][0].ToString();
                        objDashBoardCReport.Male = dt.Tables[0].Rows[i][1].ToString();
                        objDashBoardCReport.Female = dt.Tables[0].Rows[i][2].ToString();
                        objDashBoardCReport.Others = dt.Tables[0].Rows[i][3].ToString();

                        DashboardCReport_List.Add(objDashBoardCReport);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return DashboardCReport_List;
        }


        #endregion
        #region Admit Card (Aadhaar Number) Json and XML working
        List<AdmitCardAadhaar> AdmitCardAadhaar_List = null;
        public IEnumerable<AdmitCardAadhaar> XML_AdmitCardAadhar(string aadhaarno)
        {
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_ReturnAdmitcardData", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Adh", aadhaarno);
                cmd.Parameters.AddWithValue("@Refid", "");
                cmd.Parameters.AddWithValue("@Name", "");
                cmd.Parameters.AddWithValue("@DOB", "");


                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    AdmitCardAadhaar_List = new List<AdmitCardAadhaar>();
                    AdmitCardAadhaar objAdmitCardAadhaar = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objAdmitCardAadhaar = new AdmitCardAadhaar();
                        objAdmitCardAadhaar.PostName = dt.Tables[0].Rows[i][0].ToString();
                        objAdmitCardAadhaar.Name = dt.Tables[0].Rows[i][1].ToString();
                        objAdmitCardAadhaar.FathersName = dt.Tables[0].Rows[i][2].ToString();
                        objAdmitCardAadhaar.Photo = dt.Tables[0].Rows[i][3].ToString();
                        objAdmitCardAadhaar.Signature = dt.Tables[0].Rows[i][4].ToString();
                        objAdmitCardAadhaar.Address = dt.Tables[0].Rows[i][5].ToString();
                        objAdmitCardAadhaar.District = dt.Tables[0].Rows[i][6].ToString();
                        objAdmitCardAadhaar.RollNo = dt.Tables[0].Rows[i][7].ToString();
                        objAdmitCardAadhaar.ExamCenter = dt.Tables[0].Rows[i][8].ToString();
                        objAdmitCardAadhaar.CenterAddress = dt.Tables[0].Rows[i][9].ToString();
                        objAdmitCardAadhaar.DateofExamination = dt.Tables[0].Rows[i][10].ToString();
                        objAdmitCardAadhaar.ReportingTime = dt.Tables[0].Rows[i][11].ToString();
                        objAdmitCardAadhaar.Duration = dt.Tables[0].Rows[i][12].ToString();

                        AdmitCardAadhaar_List.Add(objAdmitCardAadhaar);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return AdmitCardAadhaar_List;
        }
        public IEnumerable<AdmitCardAadhaar> JSON_AdmitCardAadhar(string aadhaarno)
        {
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_ReturnAdmitcardData", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Adh", aadhaarno);
                cmd.Parameters.AddWithValue("@Refid", "");
                cmd.Parameters.AddWithValue("@Name", "");
                cmd.Parameters.AddWithValue("@DOB", "");


                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    AdmitCardAadhaar_List = new List<AdmitCardAadhaar>();
                    AdmitCardAadhaar objAdmitCardAadhaar = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objAdmitCardAadhaar = new AdmitCardAadhaar();
                        objAdmitCardAadhaar = new AdmitCardAadhaar();
                        objAdmitCardAadhaar.PostName = dt.Tables[0].Rows[i][0].ToString();
                        objAdmitCardAadhaar.Name = dt.Tables[0].Rows[i][1].ToString();
                        objAdmitCardAadhaar.FathersName = dt.Tables[0].Rows[i][2].ToString();
                        objAdmitCardAadhaar.Photo = dt.Tables[0].Rows[i][3].ToString();
                        objAdmitCardAadhaar.Signature = dt.Tables[0].Rows[i][4].ToString();
                        objAdmitCardAadhaar.Address = dt.Tables[0].Rows[i][5].ToString();
                        objAdmitCardAadhaar.District = dt.Tables[0].Rows[i][6].ToString();
                        objAdmitCardAadhaar.RollNo = dt.Tables[0].Rows[i][7].ToString();
                        objAdmitCardAadhaar.ExamCenter = dt.Tables[0].Rows[i][8].ToString();
                        objAdmitCardAadhaar.CenterAddress = dt.Tables[0].Rows[i][9].ToString();
                        objAdmitCardAadhaar.DateofExamination = dt.Tables[0].Rows[i][10].ToString();
                        objAdmitCardAadhaar.ReportingTime = dt.Tables[0].Rows[i][11].ToString();
                        objAdmitCardAadhaar.Duration = dt.Tables[0].Rows[i][12].ToString();

                        AdmitCardAadhaar_List.Add(objAdmitCardAadhaar);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return AdmitCardAadhaar_List;
        }

        #endregion


        #region Admit Card (Personal Details) Json and XML Not Tested
        List<AdmitCardAadhaar> AdmitCardPersonalDetails_List = null;
        public IEnumerable<AdmitCardAadhaar> XML_AdmitCardPersonalDetails(AdmitCard_PersonalDetails details)
        {
            //Date DD/MM/YY
            string new_dateOfbirth = details.DOB_Service.Replace("-", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_ReturnAdmitcardData", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Adh", "");
                cmd.Parameters.AddWithValue("@Refid", details.ApplicationID_Service);
                cmd.Parameters.AddWithValue("@Name", details.Name_Service);
                cmd.Parameters.AddWithValue("@DOB", new_dateOfbirth);


                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    AdmitCardPersonalDetails_List = new List<AdmitCardAadhaar>();
                    AdmitCardAadhaar objAdmitCardPersonalDetails = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {

                        objAdmitCardPersonalDetails = new AdmitCardAadhaar();
                        objAdmitCardPersonalDetails.PostName = dt.Tables[0].Rows[i][0].ToString();
                        objAdmitCardPersonalDetails.Name = dt.Tables[0].Rows[i][1].ToString();
                        objAdmitCardPersonalDetails.FathersName = dt.Tables[0].Rows[i][2].ToString();
                        objAdmitCardPersonalDetails.Photo = dt.Tables[0].Rows[i][3].ToString();
                        objAdmitCardPersonalDetails.Signature = dt.Tables[0].Rows[i][4].ToString();
                        objAdmitCardPersonalDetails.Address = dt.Tables[0].Rows[i][5].ToString();
                        objAdmitCardPersonalDetails.District = dt.Tables[0].Rows[i][6].ToString();
                        objAdmitCardPersonalDetails.RollNo = dt.Tables[0].Rows[i][7].ToString();
                        objAdmitCardPersonalDetails.ExamCenter = dt.Tables[0].Rows[i][8].ToString();
                        objAdmitCardPersonalDetails.CenterAddress = dt.Tables[0].Rows[i][9].ToString();
                        objAdmitCardPersonalDetails.DateofExamination = dt.Tables[0].Rows[i][10].ToString();
                        objAdmitCardPersonalDetails.ReportingTime = dt.Tables[0].Rows[i][11].ToString();
                        objAdmitCardPersonalDetails.Duration = dt.Tables[0].Rows[i][12].ToString();

                        AdmitCardPersonalDetails_List.Add(objAdmitCardPersonalDetails);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return AdmitCardPersonalDetails_List;

        }
        public IEnumerable<AdmitCardAadhaar> JSON_AdmitCardPersonalDetails(AdmitCard_PersonalDetails details)
        {
            //Date DD/MM/YY
            string new_dateOfbirth = details.DOB_Service.Replace("-", "/");
            SqlDataReader reader = null;
            try
            {
                dbConnection = DBConnect.getConnection();

                if (dbConnection.State.ToString() == "Closed")
                {
                    dbConnection.Open();
                }

                DataSet dt = new DataSet();
                SqlCommand cmd = new SqlCommand("sp_ReturnAdmitcardData", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Adh", "");
                cmd.Parameters.AddWithValue("@Refid", details.ApplicationID_Service);
                cmd.Parameters.AddWithValue("@Name", details.Name_Service);
                cmd.Parameters.AddWithValue("@DOB", new_dateOfbirth);


                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    // Convert DataSet to List
                    AdmitCardPersonalDetails_List = new List<AdmitCardAadhaar>();
                    AdmitCardAadhaar objAdmitCardPersonalDetails = null;
                    for (int i = 0; i < dt.Tables[0].Rows.Count; i++)
                    {
                        objAdmitCardPersonalDetails = new AdmitCardAadhaar();
                        objAdmitCardPersonalDetails = new AdmitCardAadhaar();
                        objAdmitCardPersonalDetails.PostName = dt.Tables[0].Rows[i][0].ToString();
                        objAdmitCardPersonalDetails.Name = dt.Tables[0].Rows[i][1].ToString();
                        objAdmitCardPersonalDetails.FathersName = dt.Tables[0].Rows[i][2].ToString();
                        objAdmitCardPersonalDetails.Photo = dt.Tables[0].Rows[i][3].ToString();
                        objAdmitCardPersonalDetails.Signature = dt.Tables[0].Rows[i][4].ToString();
                        objAdmitCardPersonalDetails.Address = dt.Tables[0].Rows[i][5].ToString();
                        objAdmitCardPersonalDetails.District = dt.Tables[0].Rows[i][6].ToString();
                        objAdmitCardPersonalDetails.RollNo = dt.Tables[0].Rows[i][7].ToString();
                        objAdmitCardPersonalDetails.ExamCenter = dt.Tables[0].Rows[i][8].ToString();
                        objAdmitCardPersonalDetails.CenterAddress = dt.Tables[0].Rows[i][9].ToString();
                        objAdmitCardPersonalDetails.DateofExamination = dt.Tables[0].Rows[i][10].ToString();
                        objAdmitCardPersonalDetails.ReportingTime = dt.Tables[0].Rows[i][11].ToString();
                        objAdmitCardPersonalDetails.Duration = dt.Tables[0].Rows[i][12].ToString();

                        AdmitCardPersonalDetails_List.Add(objAdmitCardPersonalDetails);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            return AdmitCardPersonalDetails_List;

        }
        #endregion
        #region notifications JSON and XML Not Implemented
        #endregion

        #region Login/ OTP JSON and XML
        private bool ValidateOTP(string Mobile, string OTP)
        {

            bool valid = false;
            dbConnection = DBConnect.getConnection();
            SqlCommand cmd = new SqlCommand("Select transID from tbl_OTP where MobileNo=@mob and OTP=@otp and convert(varchar(20),[Datetime],103)=@date and IsActive='true'");
            try
            {
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@mob", Mobile);
                cmd.Parameters.AddWithValue("@otp", OTP);
                cmd.Parameters.AddWithValue("@date", DateTime.Now.ToString("dd/MM/yyyy").Replace("-", "/"));
                cmd.Connection = dbConnection;
                cmd.CommandType = CommandType.Text;
                if (dbConnection.State == ConnectionState.Closed)
                {
                    dbConnection.Open();
                }
                DataTable dt = new DataTable();
                using (SqlDataReader dr = cmd.ExecuteReader())
                {

                    dt.Load(dr);

                }
                if (dt.Rows.Count > 0)
                {
                    valid = true;
                    cmd = new SqlCommand("update tbl_OTP set IsActive='false' where transID=@id");
                    cmd.Parameters.Clear();
                    cmd.Parameters.AddWithValue("@id", dt.Rows[0]["transID"].ToString());
                    cmd.Connection = dbConnection;
                    cmd.CommandType = CommandType.Text;
                    if (dbConnection.State == ConnectionState.Closed)
                    {
                        dbConnection.Open();
                    }
                    cmd.ExecuteNonQuery();

                }
            }
            catch { }
            finally
            { cmd.Dispose(); dbConnection.Close(); }


            return valid;
        }

        public bool XML_LoginAuth(string Mobile, string OTP)
        {
            return ValidateOTP(Mobile,OTP);
        }

        public bool JSON_LoginAuth(string Mobile, string OTP)
        {
            return ValidateOTP(Mobile, OTP);
        }

        private String GeneratOTP()
        {

            var chars = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
            var random = new Random();
            var result = new string(
                Enumerable.Repeat(chars, 6)
                          .Select(s => s[random.Next(s.Length)])
                          .ToArray());
            return result.ToString();


        }
        string OTP = "0";
        private void OTPbyEmail(string Email)
        {
            if (OTP == "0")
            {
                OTP = GeneratOTP();

            }
            if (SaveOTPInfo(Email, OTP) == true)
            {
                SendMail(Email, OTP);
            }
        }

        private void SendMail(string emailid, string otp)
        {
            try
            {
                // String Email = dt.Rows[0]["UName"].ToString();
                if (emailid != "")
                {
                    System.Net.Mail.MailMessage mail = new System.Net.Mail.MailMessage();
                    mail.From = new MailAddress("hpuid@hp.gov.in");
                    mail.To.Add(emailid);
                    String Body = "Your OTP for HPSSSB application is: " + otp.ToString() + ". Valid for 2 hours only.";
                    mail.Subject = "OTP for HPSSSB application.";
                    mail.Body = Body;
                    mail.Priority = MailPriority.High;
                    SmtpClient smtp = new SmtpClient("10.241.8.51", 25);
                    smtp.Credentials = new NetworkCredential("hpuid", "test@123");
                    try
                    {
                        smtp.Send(mail);
                    }
                    catch
                    { }
                }
            }

            catch (Exception er)
            {
                //ScriptManager.RegisterStartupScript(this, GetType(), "SSSB", "alert('" + er.Message.ToString() + "');", true);
            }
        }

        private bool SaveOTPInfo(string Mobile, string OTP)
        {
            bool com = false;
            dbConnection = DBConnect.getConnection();
             SqlCommand cmd = new SqlCommand("Insert into tbl_OTP (MobileNo,OTP,Enc_OTP,IsActive,Datetime) values(@Mobile,@otp,@EOTP,@Active,@Date)");
             try
             {
                 cmd.Parameters.Clear();
                 cmd.Parameters.AddWithValue("@Mobile", Mobile);
                 cmd.Parameters.AddWithValue("@otp", OTP);
                 cmd.Parameters.AddWithValue("@EOTP", MD5Hash(OTP));
                 cmd.Parameters.AddWithValue("@Active", "true");
                 cmd.Parameters.AddWithValue("@date", DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                 cmd.Connection = dbConnection;
                 cmd.CommandType = CommandType.Text;
                 if (dbConnection.State == ConnectionState.Closed)
                 {
                     dbConnection.Open();
                 }
                 //DataTable dt = new DataTable();
                 cmd.ExecuteNonQuery(); com = true;
             }
             catch
             { }
             return com;

                
            
        }

        private string MD5Hash(string OTP)
        {
            MD5 md5 = new MD5CryptoServiceProvider();
            //compute hash from the bytes of text
            md5.ComputeHash(ASCIIEncoding.ASCII.GetBytes(OTP));
            //get hash result after compute it
            byte[] result = md5.Hash;
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < result.Length; i++)
            {
                //change it into 2 hexadecimal digits
                //for each byte
                strBuilder.Append(result[i].ToString("x2"));
            }

            return strBuilder.ToString();
        }

        public string XML_Login(string Mobile)
        {
            string ResponseMsz = "";
            if (OTP == "0")
            {
                OTP = GeneratOTP();

            }
            if (SaveOTPInfo(Mobile, OTP) == true)
            {

                Stream dataStream;
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create("http://msdgweb.mgov.gov.in/esms/sendsmsrequest");
                request.ProtocolVersion = HttpVersion.Version10;

                ((HttpWebRequest)request).UserAgent = "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)";
                request.Method = "POST";
                try
                {
                    String smsservicetype = "bulkmsg";
                    String query = "username=" + HttpUtility.UrlEncode("hpgovt") +
                        "&password=" + HttpUtility.UrlEncode("hpdit@1234") +
                        "&smsservicetype=" + HttpUtility.UrlEncode(smsservicetype) +
                        "&content=" + HttpUtility.UrlEncode("Your OTP for HPSSSB application is: " + OTP.ToString() + ". Valid for 2 hours only.") +
                        "&bulkmobno=" + HttpUtility.UrlEncode(Mobile) +
                        "&senderid=" + HttpUtility.UrlEncode("hpgovt");
                    byte[] byteArray = Encoding.ASCII.GetBytes(query);
                    request.ContentType = "application/x-www-form-urlencoded";
                    request.ContentLength = byteArray.Length;
                    dataStream = request.GetRequestStream();
                    dataStream.Write(byteArray, 0, byteArray.Length);

                    dataStream.Close();
                    WebResponse response = request.GetResponse();
                    String Status = ((HttpWebResponse)response).StatusDescription;
                    dataStream = response.GetResponseStream();
                    StreamReader reader = new StreamReader(dataStream);
                    string responseFromServer = reader.ReadToEnd();
                    reader.Close();
                    dataStream.Close();
                    ResponseMsz="One time password (OTP) has been sent to xxxxxxx" + Mobile.Substring(7,3).ToString() +".";
                }
                catch (Exception ex)
                {
                    ResponseMsz = ex.Message.ToString();
                    // ScriptManager.RegisterStartupScript(//(this, GetType(), "Online Self Seeding", "alert('"+er.Message.ToString()+"');", true);
                }
                //SendMail(txt_EmailID.Text.Trim(), OTP);
                
            }
            return ResponseMsz;
        }

        public string JSON_Login(string Mobile)
        {
            string ResponseMsz = "";
            if (OTP == "0")
            {
                OTP = GeneratOTP();

            }
            if (SaveOTPInfo(Mobile, OTP) == true)
            {

                Stream dataStream;
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create("http://msdgweb.mgov.gov.in/esms/sendsmsrequest");
                request.ProtocolVersion = HttpVersion.Version10;

                ((HttpWebRequest)request).UserAgent = "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)";
                request.Method = "POST";
                try
                {
                    String smsservicetype = "bulkmsg";
                    String query = "username=" + HttpUtility.UrlEncode("hpgovt") +
                        "&password=" + HttpUtility.UrlEncode("hpdit@1234") +
                        "&smsservicetype=" + HttpUtility.UrlEncode(smsservicetype) +
                        "&content=" + HttpUtility.UrlEncode("Your OTP for HPSSSB application is: " + OTP.ToString() + ". Valid for 2 hours only.") +
                        "&bulkmobno=" + HttpUtility.UrlEncode(Mobile) +
                        "&senderid=" + HttpUtility.UrlEncode("hpgovt");
                    byte[] byteArray = Encoding.ASCII.GetBytes(query);
                    request.ContentType = "application/x-www-form-urlencoded";
                    request.ContentLength = byteArray.Length;
                    dataStream = request.GetRequestStream();
                    dataStream.Write(byteArray, 0, byteArray.Length);

                    dataStream.Close();
                    WebResponse response = request.GetResponse();
                    String Status = ((HttpWebResponse)response).StatusDescription;
                    dataStream = response.GetResponseStream();
                    StreamReader reader = new StreamReader(dataStream);
                    string responseFromServer = reader.ReadToEnd();
                    reader.Close();
                    dataStream.Close();
                    ResponseMsz = "One time password (OTP) has been sent to xxxxxxx" + Mobile.Substring(7, 3).ToString() + ".";
                }
                catch(Exception ex)
                {
                    ResponseMsz = ex.Message.ToString();
                    // ScriptManager.RegisterStartupScript(//(this, GetType(), "Online Self Seeding", "alert('"+er.Message.ToString()+"');", true);
                }
                //SendMail(txt_EmailID.Text.Trim(), OTP);

            }
            return ResponseMsz;
        }
        #endregion
    }
}
