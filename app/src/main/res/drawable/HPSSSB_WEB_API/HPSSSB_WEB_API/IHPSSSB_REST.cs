﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Web.Script.Services;

namespace HPSSSB_WEB_API
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IHPSSSB_REST" in both code and config file together.
    [ServiceContract]
    public interface IHPSSSB_REST
    {
        #region Test Functions Restful Services JSON and XML


        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getxml/{id}")]
        string XMLData(string id);

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getjson/{id}")]
        string JSONData(string id);

        #endregion  
        #region Get Instructions (PDF File Link) in XML and JSON
        [OperationContract]
        [WebInvoke(Method ="GET",  ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate ="getInstructions_XML")]
        string XML_getInstructions();

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getInstructions_JSON")]
        string JSON_getInstructions();

        #endregion
        #region Get All Vacancies JSON and XML 
        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getVacancies_XML/{date}")]
        IEnumerable<Vacancies> XML_Vacancies(string date);

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getVacancies_JSON/{date}")]
        IEnumerable<Vacancies> JSON_Vacancies(string date);
        #endregion
        #region Dashboard {sp_Dashboard}  XML and JSON
        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getDashboard_XML/{FromDate}/{ToDate}")]
        IEnumerable<DashboardBank> XML_DashBoard (string fromdate , string todate);

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getDashboard_JSON/{FromDate}/{ToDate}")]
        IEnumerable<DashboardBank> JSON_Dashboard (string fromdate , string todate);

        #endregion
        #region Dashboard Consolodated Report XML and JSON
        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getDashboardCReport_XML/{FromDate}/{ToDate}")]
        IEnumerable<DashboardCReport> XML_DashBoardCReport(string fromdate, string todate);

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getDashboardCReport_JSON/{FromDate}/{ToDate}")]
        IEnumerable<DashboardCReport> JSON_DashboardCReport(string fromdate, string todate);
        #endregion
        #region Admit Card (Aadhaar Card) Json and XML
        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getAdmitCardAadhaar_XML/{aadhaarno}")]
        IEnumerable<AdmitCardAadhaar> XML_AdmitCardAadhar(string aadhaarno);

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getAdmitCardAadhaar_JSON/{aadhaarno}")]
        IEnumerable<AdmitCardAadhaar> JSON_AdmitCardAadhar(string aadhaarno);
        #endregion
        #region Admit Card (Personal Details) Json and XML
        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getAdmitCardPersonalDetails_XML/{aplicationId}/{name}/{dateofbirth}")]
        IEnumerable<AdmitCardAadhaar> XML_AdmitCardPersonalDetails(string aplicationId, string name , string DateOfBirth);

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "getAdmitCardPersonalDetails_JSON/{aplicationId}/{name}/{dateofbirth}")]
        IEnumerable<AdmitCardAadhaar> JSON_AdmitCardPersonalDetails(string aplicationId, string name, string DateOfBirth);
        #endregion
        #region notifications JSON and XML Not Implemented
        #endregion

    }
}
