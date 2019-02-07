package com.hibernateTest.hibernateTest.repository.basicAuthorization;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserBasic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userBasicRepository")
public interface UserBasicRepository extends CrudRepository<UserBasic, String> { }
