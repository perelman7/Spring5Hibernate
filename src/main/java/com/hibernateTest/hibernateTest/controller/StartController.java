package com.hibernateTest.hibernateTest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.LinkedHashMap;

@RestController
@RequestMapping
@Api(value="start", description="redirect to the main page (main.html)")
public class StartController {

    @GetMapping
    @ApiOperation(value = "if user is authorized: return main page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return main page"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public RedirectView mainPage(){
        return new RedirectView("main.html");
    }

    @GetMapping("/singinG")
    @ApiOperation(value = "return info about authorized user", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return info about authorized user"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public String singin(OAuth2Authentication auth){
        Authentication a = auth.getUserAuthentication();
        LinkedHashMap<String, String> o = (LinkedHashMap<String, String>)a.getDetails();

        Object ob = a.getAuthorities();
        Object[] ooo = a.getAuthorities().toArray();
        int lng = ooo.length;
        SimpleGrantedAuthority ss = (SimpleGrantedAuthority)ooo[0];
        String role = ss.getAuthority();


        String id = o.get("id");
        String email = o.get("email");
        String name = o.get("given_name");
        String surname= o.get("family_name");

        String result = "Authorize {id: " + id + ", email: " + email + ", name: " + name + ", surname: " + surname + ", role: " +role;
        return result;
    }
}
