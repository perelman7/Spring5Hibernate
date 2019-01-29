package com.hibernateTest.hibernateTest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateTimeFormatter {

    public static LocalDateTime format(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime.format(formatter), formatter);
    }
}
