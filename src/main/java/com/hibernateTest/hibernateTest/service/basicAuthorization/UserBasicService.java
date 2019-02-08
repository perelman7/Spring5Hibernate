package com.hibernateTest.hibernateTest.service.basicAuthorization;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserBasic;

import java.util.List;

public interface UserBasicService {

    List<UserBasic> getAll();
    UserBasic getByUsername(String username);
    UserBasic add(UserBasic user);
    boolean delete(String username);

}
