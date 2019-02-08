package com.hibernateTest.hibernateTest.repository.basicAuthorization;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserBasic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userBasicRepository")
public interface UserBasicRepository extends CrudRepository<UserBasic, String> {

    @Query(value = "select u.username, u.passwd, u.enable from UserBasic as u where u.username= :username")
    UserBasic findByUsername(@Param("username") String username);

}
