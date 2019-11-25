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

        [Authorize(Roles = "Admin")]
        [HttpPost]
        [Route("entry")]
        public ActionResult<string> EntryAttempt([FromHeader] string plate)
        {
            var stored = db.StoredPlates.Where(x => x.Plate == plate).FirstOrDefault();
            if (stored != null)
            {
                LogEntry(stored);
                switch (stored.Action)
                {
                    case GateAction.OPEN:
                        //open gate
                        return Ok(new { message = $"Megérkezett {stored.Name}, kapu automatikusan kinyitva." });
                    default:
                    case GateAction.NOTIFY:
                    case GateAction.REFUSE:
                        //Firebase action here
                        return Ok(new { message = $"Megérkezett {stored.Name}, további művelet szükséges." });
                }
            }
            else
            {
                LogEntry(plate);
                return Ok(new { message = "Valaki itt van a kapunál." });
            }
        }

        private void LogEntry(StoredPlates stored)
        {
            db.EntryLogs.Add(new EntryLog(){
                Plate = stored.Plate,
                Time = DateTime.Now,
                Outcome = stored.Action
            });
            db.SaveChanges();
        }

        private void LogEntry(string plate)
        {
            db.EntryLogs.Add(new EntryLog()
            {
                Plate = plate,
                Time = DateTime.Now,
                Outcome = GateAction.NOTIFY
            });
            db.SaveChanges();
        }
    }
}