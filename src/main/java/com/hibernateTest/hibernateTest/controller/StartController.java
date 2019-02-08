package com.hibernateTest.hibernateTest.controller;

import com.hibernateTest.hibernateTest.service.jwtService.JwtTokenProvider;
import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserBasic;
import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserRoleBasic;
import com.hibernateTest.hibernateTest.repository.basicAuthorization.UserRoleBasicRepository;
import com.hibernateTest.hibernateTest.service.ChangeRowService;
import com.hibernateTest.hibernateTest.service.basicAuthorization.defaultImplementation.DefaultUserBasicService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
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

    @Autowired
    @Qualifier("jwtTokenProvider")
    private JwtTokenProvider provider;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder encoder;

    @Autowired
    @Qualifier("defaultUserBasicService")
    private DefaultUserBasicService userService;

    @Autowired
    @Qualifier("userRoleBasicRepository")
    private UserRoleBasicRepository roleRepository;

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

        List<UserRoleBasic> roles = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i++){
            GrantedAuthority grantedAuthority = (GrantedAuthority) authorities.toArray()[i];
            UserRoleBasic role = new UserRoleBasic();
            role.setRole(grantedAuthority.getAuthority());
            roles.add(role);
        }
        String name = user.getUsername();

        String token = provider.createToken(name, roles);

        return "Name: " + name + ", with role: " + roles.toString() +", token: " + token ;
    }

    @PostMapping("/singup")
    public RedirectView singup(@RequestParam("username") String username, @RequestParam("password") String password){
        System.out.println(username + " " + password);

        UserBasic user = new UserBasic();
        user.setUsername(username);
        user.setPasswd(encoder.encode(password));
        user.setEnable(true);

        UserRoleBasic role = new UserRoleBasic();
        role.setRole("ROLE_USER");
        role.setUserBasic(user);

        userService.add(user);
        roleRepository.save(role);

        return new RedirectView("welcome.html");

    }
}
