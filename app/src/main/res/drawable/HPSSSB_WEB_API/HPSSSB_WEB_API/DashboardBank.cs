using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace HPSSSB_WEB_API
{
    [Serializable, DataContract(Name = "DashboardBank")]
  public  class DashboardBank
    {

        [DataMember]
        public String Application_Recived { get; set; }

        [DataMember]
        public String Total_Payment_Received { get; set; }

        [DataMember]
        public String PNB { get; set; }

        [DataMember]
        public String HDFC { get; set; }

        [DataMember]
        public String LMK { get; set; }

        [DataMember]
        public String Offline { get; set; }

       
    }
}
