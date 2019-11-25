using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace OpenMyGarageApi.Models
{
    public class StoredPlates
    {
        [Key]
        public int ID { get; set; }

        [Required]
        public string Plate { get; set; }

        [Required]
        public string Name { get; set; }

        [Required]
        public GateAction Action { get; set; }
    }
}
