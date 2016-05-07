using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace WEB_API_HPSSSB
{
    [Serializable, DataContract(Name = "AdmitCard_PersonalDetails")]

    public class AdmitCard_PersonalDetails
    {
        [DataMember]
        public String Name_Service { get; set; }

        [DataMember]
        public String DOB_Service { get; set; }

        [DataMember]
        public String ApplicationID_Service { get; set; }


    }
}
