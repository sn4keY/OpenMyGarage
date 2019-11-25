﻿using System;
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
    }
}