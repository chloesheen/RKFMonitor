package com.example.app.util;

public class Pair<E, T> {

    private E mFirst;
    private T mSecond;

    public Pair(E first, T second) {
        mFirst = first;
        mSecond = second;
    }

    public E getFirst() {
        return mFirst;
    }

    public T getSecond() {
        return mSecond;
    }

    public boolean isEquals(Pair one) {
        return true;
    }
}
