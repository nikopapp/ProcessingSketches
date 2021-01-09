package com.nikpappas.processing.benchmark;

import java.util.function.Function;

public class Timing {
    public static void timed(Runnable func){
        long start = System.currentTimeMillis();
        func.run();
        System.out.println(System.currentTimeMillis()-start);
    }
    public static void timed(Runnable func, String name){
        long start = System.currentTimeMillis();
        func.run();
        System.out.print(System.currentTimeMillis()-start);
        System.out.println(" "+name);
    }

}
