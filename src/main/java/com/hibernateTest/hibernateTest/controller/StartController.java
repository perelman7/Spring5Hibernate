package com.hibernateTest.hibernateTest.controller;

import com.hibernateTest.hibernateTest.service.ChangeRowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping
@Api(value="start", description="redirect to the main page (main.html)")
public class StartController {

    @Autowired
    @Qualifier("defaultChangeRowService")
    private ChangeRowService service;

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
        Authentication authentication = auth.getUserAuthentication();
        LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>)authentication.getDetails();

        List authorities = (List) authentication.getAuthorities();
        SimpleGrantedAuthority arantedAuthority = (SimpleGrantedAuthority) authorities.get(0);
        String role = arantedAuthority.getAuthority();

        String id = userDetails.get("id");
        String email = userDetails.get("email");
        String name = userDetails.get("given_name");
        String surname = userDetails.get("family_name");

        return "Authorize {id: " + id + ", email: " + email + ", name: " + name + ", surname: " + surname + ", role: " +role;
    }

    @GetMapping("/test")
    public String test(Authentication authentication){

        User user = (User)authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        GrantedAuthority grantedAuthority = (GrantedAuthority) authorities.toArray()[0];
        String role = grantedAuthority.getAuthority();
        String name = user.getUsername();

        return "Name: " + name + ", with role: " + role ;
    }
}
