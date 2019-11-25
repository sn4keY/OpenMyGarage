using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OpenMyGarageApi.Data;
using OpenMyGarageApi.Models;

namespace OpenMyGarageApi.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class ValuesController : ControllerBase
    {
        private readonly ApplicationDbContext db;

        public ValuesController(ApplicationDbContext db)
        {
            this.db = db;
        }

        [HttpGet]
        [Route("entrylogs")]
        public ActionResult<IEnumerable<EntryLog>> GetEntryLogs()
        {
            return db.EntryLogs;
        }

        [HttpGet]
        [Route("storedplates")]
        public ActionResult<IEnumerable<StoredPlates>> GetStoredPlates()
        {
            return db.StoredPlates;
        }

        [Authorize(Roles = "Admin")]
        [HttpPost]
        [Route("storedplates")]
        public void AddStoredPlate([FromBody] StoredPlates plate)
        {
            db.StoredPlates.Add(plate);
            db.SaveChanges();
        }
    }
}