package com.stx.java.test.util;

import com.stx.java.util.ArrayList;

import java.util.List;

public class ArrayListTest {

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i ++){
            list.add(String.valueOf(i));
        }
        for (int i = 70; i > 50; i --){
            list.remove(i);
        }
        Object[] strArr = list.toArray();
        for (int i = 0; i < strArr.length; i ++){
            System.out.println(strArr[i]);
        }
    }
}
