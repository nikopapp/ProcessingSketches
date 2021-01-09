package com.nikpappas.processing.core;

public class Pair<T,V> {
    public final T _1;
    public final V _2;

    private Pair(T _1,V _2){
        this._1 = _1;
        this._2 = _2;
    }

    public static <X,Y> Pair<X,Y> of(X _1, Y _2){
        return new Pair<>(_1,_2);
    }

    @Override
    public String toString() {
        return "(" +
                 _1 +
                "," + _2 +
                ')';
    }
}
