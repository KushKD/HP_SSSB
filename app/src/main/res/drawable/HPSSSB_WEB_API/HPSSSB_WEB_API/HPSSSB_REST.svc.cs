﻿using HPSSSB_WEB_API.HelperClasses;
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
        public string XML_Vacancies(string date) 
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
                }
                catch
                {

                }
                finally
                {
                    adp.Dispose();
                }
            }catch(Exception e)
            {
                return "null";
            }

            //return dt;
            throw new NotImplementedException();
        }

        public string JSON_Vacancies(string date)
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
                SqlCommand cmd = new SqlCommand("GetAllCurrentVacancies", dbConnection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Ldate", date);
                cmd.Parameters.AddWithValue("@PDate", date);

                SqlDataAdapter adp = new SqlDataAdapter(cmd);
                try
                {
                    adp.Fill(dt);
                    //Convert DT to List
                }
                catch
                {

                }
                finally
                {
                    adp.Dispose();
                }
            }
            catch (Exception e)
            {
                return "null";
            }

            //return dt;

            throw new NotImplementedException();
        }
        #endregion



    }
}
