package com.nikpappas.processing.core;

public class Triplet<T, V, U> {
    public final T _1;
    public final V _2;
    public final U _3;

    private Triplet(T _1, V _2, U _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public static <X, Y, Z> Triplet<X, Y, Z> of(X _1, Y _2, Z _3) {
        return new Triplet<>(_1, _2, _3);
    }

}
