package com.leyvadev.iesjrecontrol.logincontrol.resources;

import com.leyvadev.iesjrecontrol.logincontrol.dto.LoginResponse;
import com.leyvadev.iesjrecontrol.logincontrol.services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/login")
@Produces("application/json")
public class LoginResources {
    @Inject
    LoginService loginService;

    @POST
    public LoginResponse<String> login(@QueryParam("username") String username, @QueryParam("password") String password) throws Exception {
        return loginService.login(username, password);
    }

}
