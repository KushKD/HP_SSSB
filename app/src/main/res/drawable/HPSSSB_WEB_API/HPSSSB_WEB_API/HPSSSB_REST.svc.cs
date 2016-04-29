using HPSSSB_WEB_API.HelperClasses;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace HPSSSB_WEB_API
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "HPSSSB_REST" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select HPSSSB_REST.svc or HPSSSB_REST.svc.cs at the Solution Explorer and start debugging.
    public class HPSSSB_REST : IHPSSSB_REST
    {

        SqlConnection dbConnection;

        #region Basic Testing Functions Rest Architecture

        public string XMLData(string id)
        {
            return id;
        }


        public string JSONData(string id)
        {
            return id;
        }

        #endregion

        #region get Instructions XML and JSON Link
       

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

        #region Get Vacancies XML and JSON
        List<Vacancies> vacancy_List = null;
        public IEnumerable<Vacancies> XML_Vacancies(string date) 
        {
            string new_date = date.Replace(".", "/");
            SqlDataReader reader = null;
            try {
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
            }catch(Exception e)
            {
                throw e;
            }

            return vacancy_List;
        }
        public IEnumerable<Vacancies> JSON_Vacancies(string date)
        {
            string new_date = date.Replace(".", "/");
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

        #region Dashboard Bank for XML and JSON
        List<DashboardBank> DashboardBank_List = null;
        public IEnumerable<DashboardBank> XML_DashBoard(string fromdate, string todate)
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
                cmd.Parameters.AddWithValue("@FrmDate", new_fromdate);
                cmd.Parameters.AddWithValue("@TDate", new_todate);

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
        public IEnumerable<DashboardBank> JSON_Dashboard(string fromdate , string todate) 
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
                cmd.Parameters.AddWithValue("@FrmDate", new_fromdate);
                cmd.Parameters.AddWithValue("@TDate", new_todate);

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
                SqlCommand cmd = new SqlCommand("sp_GetConsolidatedReport", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@FrmDate", new_fromdate);
                cmd.Parameters.AddWithValue("@TDate", new_todate);

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
                SqlCommand cmd = new SqlCommand("sp_GetConsolidatedReport", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@FrmDate", new_fromdate);
                cmd.Parameters.AddWithValue("@TDate", new_todate);

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



    }
}
