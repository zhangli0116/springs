package com.java.java8;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Create by Administrator on 2018/10/25
 *  lambda表达式 应用
 * @author admin
 */
public class LengthComparator implements Comparator<String> {

    @Override
    public int compare(String first, String second) {
        return first.length() - second.length();
    }

    public static void main(String[] args) {
        String[] strings = {"apple","back","cap","element"};
        Arrays.sort(strings,new LengthComparator());
        Arrays.sort(strings,(first,second)->first.length() - second.length());
        Arrays.sort(strings,Comparator.comparing(String::length));//改进

    }
}
