using System;
using System.Runtime.Serialization;

namespace HPSSSB_WEB_API
{
    [Serializable, DataContract(Name = "AdmitCardAadhaar")]
    public class AdmitCardAadhaar
    {
        [DataMember]
        public String App_RollNo { get; set; }

        [DataMember]
        public String PostName { get; set; }

        [DataMember]
        public String PerName { get; set; }

        [DataMember]
        public String PerFName { get; set; }

        [DataMember]
        public String PerPhoto { get; set; }

        [DataMember]
        public String PerSignature { get; set; }

        [DataMember]
        public String PerAddress { get; set; }

        [DataMember]
        public String DistrictName { get; set; }

        [DataMember]
        public String centreAlloted { get; set; }

        [DataMember]
        public String ApplicationFormNo { get; set; }

        [DataMember]
        public String DateofExamination { get; set; }

        [DataMember]
        public String ReportingTime { get; set; }

        [DataMember]
        public String Duration { get; set; }
    }
}