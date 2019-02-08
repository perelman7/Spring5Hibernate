package com.hibernateTest.hibernateTest.repository.basicAuthorization;

import com.hibernateTest.hibernateTest.model.basicAuthorizarion.UserRoleBasic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRoleBasicRepository")
public interface UserRoleBasicRepository extends CrudRepository<UserRoleBasic, Long> {

    @Query(value = "SELECT role.id, role.username, role.role  FROM user_role_basic AS role where role.username=:username", nativeQuery = true)
    Iterable<UserRoleBasic> findAllByUsername(@Param("username") String username);
}
