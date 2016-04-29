using System;
using System.Runtime.Serialization;

namespace HPSSSB_WEB_API
{
    [Serializable, DataContract(Name = "DashboardCReport")]
    public class DashboardCReport
    {

        [DataMember]
        public String Post_Name { get; set; }

        [DataMember]
        public String Male { get; set; }

        [DataMember]
        public String Female { get; set; }

        [DataMember]
        public String Others { get; set; }

       


    }
}