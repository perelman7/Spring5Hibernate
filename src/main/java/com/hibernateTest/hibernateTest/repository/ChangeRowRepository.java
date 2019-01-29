package com.hibernateTest.hibernateTest.repository;

import com.hibernateTest.hibernateTest.model.ChangeRow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("changeRowRepository")
public interface ChangeRowRepository extends CrudRepository<ChangeRow, Long> {

}
