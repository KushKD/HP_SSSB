using System;
using System.Runtime.Serialization;

namespace HPSSSB_WEB_API
{
    [Serializable, DataContract(Name = "AdmitCardAadhaar")]
    public class AdmitCardAadhaar
    {
        [DataMember]
        public String PostName { get; set; }

        [DataMember]
        public String Name { get; set; }

        [DataMember]
        public String FathersName { get; set; }

        [DataMember]
        public String Photo { get; set; }

        [DataMember]
        public String Signature { get; set; }

        [DataMember]
        public String Address { get; set; }

        [DataMember]
        public String District { get; set; }

        [DataMember]
        public String RollNo { get; set; }

        [DataMember]
        public String ExamCenter { get; set; }

        [DataMember]
        public String CenterAddress { get; set; }

        [DataMember]
        public String DateofExamination { get; set; }

        [DataMember]
        public String ReportingTime { get; set; }

        [DataMember]
        public String Duration { get; set; }
    }
}