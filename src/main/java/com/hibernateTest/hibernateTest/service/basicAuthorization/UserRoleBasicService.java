package com.hibernateTest.hibernateTest.service.basicAuthorization;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserRoleBasic;

import java.util.List;

public interface UserRoleBasicService {

    List<UserRoleBasic> getAll();
    List<UserRoleBasic> getAllByUsername(String username);
    UserRoleBasic add(UserRoleBasic role);
    boolean delete(String username);
}
