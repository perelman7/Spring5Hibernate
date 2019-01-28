package com.hibernateTest.hibernateTest.model;

import java.time.LocalDateTime;

public class ChangeRow {


    private long id;
    private String username;
    private String tableName;
    private String methodName;
    private String oldValue;
    private String newValue;
    private int version;
    private LocalDateTime dateChage;
}
