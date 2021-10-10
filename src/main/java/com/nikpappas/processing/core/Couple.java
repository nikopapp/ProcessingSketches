package com.nikpappas.processing.core;

public class Couple<T> {


    public final T _1;
    public final T _2;

    private Couple(T _1, T _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static <X> Couple<X> of(X _1, X _2) {
        return new Couple<>(_1, _2);
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ')';
    }
}
