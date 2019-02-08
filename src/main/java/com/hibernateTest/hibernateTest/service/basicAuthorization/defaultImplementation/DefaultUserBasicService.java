package com.hibernateTest.hibernateTest.service.basicAuthorization.defaultImplementation;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserBasic;
import com.hibernateTest.hibernateTest.repository.basicAuthorization.UserBasicRepository;
import com.hibernateTest.hibernateTest.service.basicAuthorization.UserBasicService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("defaultUserBasicService")
public class DefaultUserBasicService implements UserBasicService {

    @Autowired
    @Qualifier("userBasicRepository")
    private UserBasicRepository userRepository;

    private Logger logger = Logger.getLogger(DefaultUserBasicService.class);

    private ConvertorIteratorToList<UserBasic> convertor = new ConvertorIteratorToList<>();

    @Override
    public List<UserBasic> getAll() {
        List<UserBasic> users = convertor.convert(userRepository.findAll());
        if(users.size() > 0){
            return users;
        }else {
            logger.error("Table userbasic is empty");
            return null;
        }
    }

    @Override
    public UserBasic getByUsername(String username) {
        UserBasic user = userRepository.findByUsername(username);
        if(user != null){
            return user;
        }else {
            logger.error("User with name: " + username + " does not exist");
            return null;
        }

    }

    @Override
    public UserBasic add(UserBasic user) {
        if(user == null){
            logger.error("Value of user is null" );
            return null;
        }
        UserBasic result = userRepository.save(user);
        if(result != null){
            return result;
        }else {
            logger.error("User: " + user + " is not correct" );
            return null;
        }
    }

    @Override
    public boolean delete(String username) {
        if(username.isEmpty()){
            logger.error("Username is empty" );
            return false;
        }
        UserBasic user = this.getByUsername(username);
        if(user == null){
            logger.error("User with name:" + username + " does not exist" );
            return false;
        }
        userRepository.delete(user);
        return true;
    }
}
