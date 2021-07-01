package com.kill_rear.common.util;


public class Pair<A, B> {

    private A first;

    private B second;

    public Pair() {}

    public Pair(A a, B b){
        first = a;
        second = b;
    }
    
    public A getFirst() { return first;}
    
    public void setFirst(A a) { first = a; }

    public B getSecond() { return second; }

    public void setSecond(B b) { second = b; }

    public String toString(){
        return "(" + first + ", " + second + ")";
    }
}
