using System;
using System.Runtime.Serialization;

namespace HPSSSB_WEB_API
{
    [Serializable, DataContract(Name = "Vacancies")]
    public class Vacancies
    {
        [DataMember]
        public String SNO { get; set; }

        [DataMember]
        public String PostID { get; set; }

        [DataMember]
        public String Department { get; set; }

        [DataMember]
        public String PostName { get; set; }

        [DataMember]
        public String PostCode { get; set; }

        [DataMember]
        public String Details { get; set; }

        [DataMember]
        public String PubDate { get; set; }

        [DataMember]
        public String LastDate { get; set; }
       
    }
}