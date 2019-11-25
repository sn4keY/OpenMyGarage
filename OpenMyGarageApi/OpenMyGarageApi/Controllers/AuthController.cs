using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using OpenMyGarageApi.ViewModels;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;

namespace OpenMyGarageApi.Controllers
{
    [Route("[controller]")]
    public class AuthController : Controller
    {
        private readonly UserManager<IdentityUser> _userManager;
        private readonly IConfiguration _configuration;

        public AuthController(UserManager<IdentityUser> userManager, IConfiguration configuration)
        {
            _userManager = userManager;
            _configuration = configuration;
        }

        [HttpPost]
        [Route("register")] // /register
        public async Task<ActionResult> InsertUser([FromBody] RegisterViewModel model)
        {
            var user = new IdentityUser
            {
                Email = model.Email,
                UserName = model.Email,
                SecurityStamp = Guid.NewGuid().ToString()
            };
            var result = await _userManager.CreateAsync(user, model.Password);
            if (result.Succeeded)
            {
                await _userManager.AddToRoleAsync(user, "User");
            }
            return Ok(new { Username = user.UserName });
        }

        [HttpPost]
        [Route("login")] // /login
        public async Task<ActionResult> Login(LoginViewModel model)
        {
            var user = await _userManager.FindByNameAsync(model.Username);

            if (user != null)
            {
                var roles = await _userManager.GetRolesAsync(user);
                string role = roles.Contains("Admin") ? "Admin" : "User";
                if (await _userManager.CheckPasswordAsync(user, model.Password))
                {
                    var claim = new[] {
                    new Claim(JwtRegisteredClaimNames.Sub, user.UserName),
                    new Claim("http://schemas.microsoft.com/ws/2008/06/identity/claims/role", role)
                };
                    var signinKey = new SymmetricSecurityKey(
                      Encoding.UTF8.GetBytes(_configuration["Jwt:SigningKey"]));

                    int expiryInMinutes = Convert.ToInt32(_configuration["Jwt:ExpiryInMinutes"]);

                    var token = new JwtSecurityToken(
                      issuer: _configuration["Jwt:Site"],
                      audience: _configuration["Jwt:Site"],
                      claims: claim,
                      expires: DateTime.Now.AddMinutes(60),
                      signingCredentials: new SigningCredentials(signinKey, SecurityAlgorithms.HmacSha256)
                    );
                    return Ok(
                      new
                      {
                          token = new JwtSecurityTokenHandler().WriteToken(token),
                          expiration = token.ValidTo
                      });
                }
            }
            return Unauthorized();
        }
    }
}
