package com.hibernateTest.hibernateTest.controller;

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
public class StartController {

    @GetMapping
    public RedirectView mainPage(){
        return new RedirectView("/login.html");
    }

    @GetMapping("/singinG")
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
