package com.hibernateTest.hibernateTest.service.jwtService;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserBasic;
import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserRoleBasic;
import com.hibernateTest.hibernateTest.repository.basicAuthorization.UserBasicRepository;
import com.hibernateTest.hibernateTest.repository.basicAuthorization.UserRoleBasicRepository;
import com.hibernateTest.hibernateTest.service.basicAuthorization.defaultImplementation.DefaultUserBasicService;
import com.hibernateTest.hibernateTest.service.basicAuthorization.defaultImplementation.DefaultUserRoleBasicService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("defaultUserBasicService")
    private DefaultUserBasicService userService;

    @Autowired
    @Qualifier("defaultUserRoleBasicService")
    private DefaultUserRoleBasicService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserBasic user = userService.getByUsername(username);
        List<UserRoleBasic> userRoles = roleService.getAllByUsername(username);
        String[] roles = new String[userRoles.size()];
        for(int i = 0; i < userRoles.size(); i++){
            roles[i] = userRoles.get(i).getRole();
        }

        if(user == null && userRoles.size() <= 0){
            return null;
        }

        return User
                .withUsername(username)
                .password(user.getPasswd())
                .authorities(roles)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
