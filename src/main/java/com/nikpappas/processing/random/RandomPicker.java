package com.nikpappas.processing.random;

import java.util.List;

public class RandomPicker<T> {
    private final List<T> items;

    public RandomPicker(List<T> items) {
        this.items = items;
    }

    public T random() {
        return items.get((int) Math.round(Math.random() * (items.size() - 1)));
    }
}
