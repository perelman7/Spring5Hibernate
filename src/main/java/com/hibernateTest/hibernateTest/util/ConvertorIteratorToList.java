package com.hibernateTest.hibernateTest.util;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ConvertorIteratorToList<T> {

    public List<T> convert(Iterable<T> iterable){
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
