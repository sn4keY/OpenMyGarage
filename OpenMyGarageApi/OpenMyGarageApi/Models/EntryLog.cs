using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace OpenMyGarageApi.Models
{
    public class EntryLog
    {
        [Key]
        public int ID { get; set; }

        [Required]
        public string Plate { get; set; }

        [Required]
        public DateTime Time { get; set; }

        [Required]
        public GateAction Outcome { get; set; }
    }
}
