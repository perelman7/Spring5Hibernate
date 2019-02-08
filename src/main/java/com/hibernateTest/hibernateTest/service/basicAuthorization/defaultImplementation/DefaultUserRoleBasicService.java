package com.hibernateTest.hibernateTest.service.basicAuthorization.defaultImplementation;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserRoleBasic;
import com.hibernateTest.hibernateTest.repository.basicAuthorization.UserRoleBasicRepository;
import com.hibernateTest.hibernateTest.service.basicAuthorization.UserRoleBasicService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("defaultUserRoleBasicService")
public class DefaultUserRoleBasicService implements UserRoleBasicService {

    @Autowired
    @Qualifier("userRoleBasicRepository")
    private UserRoleBasicRepository roleRepository;

    private Logger logger = Logger.getLogger(DefaultUserBasicService.class);

    private ConvertorIteratorToList<UserRoleBasic> convertor = new ConvertorIteratorToList<>();

    @Override
    public List<UserRoleBasic> getAll() {
        List<UserRoleBasic> roles = convertor.convert(roleRepository.findAll());
        if(roles.size() > 0){
            return roles;
        }else {
            logger.error("Table user_role_basic is empty");
            return null;
        }
    }

    @Override
    public List<UserRoleBasic> getAllByUsername(String username) {
        if(username.isEmpty()){
            logger.error("Value of username is empty");
            return null;
        }
        List<UserRoleBasic> roles = convertor.convert(roleRepository.findAllByUsername(username));
        if(roles.size() > 0){
            return roles;
        }else{
            logger.error("User with name : " + username + " have not roles.");
            return null;
        }
    }

    @Override
    public UserRoleBasic add(UserRoleBasic role) {
        if(role == null){
            logger.error("Value of user`s role is null");
            return null;
        }
        UserRoleBasic result = roleRepository.save(role);
        if(result != null){
            return result;
        }else {
            logger.error("The role was not saved");
            return null;
        }
    }

    @Override
    public boolean delete(String username) {
        if(username.isEmpty()){
            logger.error("Value of username is empty");
            return false;
        }
        Iterable<UserRoleBasic> roles = roleRepository.findAllByUsername(username);
        roleRepository.deleteAll(roles);
        return true;
    }
}
