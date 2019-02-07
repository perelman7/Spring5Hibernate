package com.hibernateTest.hibernateTest.repository.basicAuthorization;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserRoleBasic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRoleBasicRepository")
public interface UserRoleBasicRepository extends CrudRepository<UserRoleBasic, Long> { }
