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



    }
}
