package com.example.controller;


public class Test {

    public static void main(String[] args) {
        int[] a = {12, 12, 43, 43, 33};
        int temp = 0;
        for (int i = 0; i < a.length; i++) {
            //temp ^= a[i];
            temp = temp ^ a[i];
            System.out.println(temp);
        }
        System.out.println(temp);

    }
}
