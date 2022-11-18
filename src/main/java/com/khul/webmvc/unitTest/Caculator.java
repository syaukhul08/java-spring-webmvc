package com.khul.webmvc.unitTest;

public class Caculator {
    public int divide(int a, int b){
        if(b == 0){
            throw new IllegalArgumentException("Can not divide by zero");
        }else {
            return a/b;
        }
    }

    public int add(int x, int y){
        return x+y;
    }

    public int multiple(int x, int y){
        return x*y;
    }
}
