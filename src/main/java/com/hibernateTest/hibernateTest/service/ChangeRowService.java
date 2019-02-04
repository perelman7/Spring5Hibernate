package com.hibernateTest.hibernateTest.service;

import com.hibernateTest.hibernateTest.model.ChangeRow;

import java.util.List;

public interface ChangeRowService {

    List<ChangeRow> getAll();
    ChangeRow getById(long id);
    ChangeRow addChangeRow(ChangeRow changeRow);
}
