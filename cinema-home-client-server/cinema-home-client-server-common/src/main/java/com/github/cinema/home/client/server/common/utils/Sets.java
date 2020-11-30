package com.github.cinema.home.client.server.common.utils;

import java.util.LinkedHashSet;

public class Sets {
    public static <T> LinkedHashSet<T> emptyLinkedSet() {
        return new LinkedHashSet<>(0);
    }

    public static <T> LinkedHashSet<T> singletonLinkedSet(T obj) {
        LinkedHashSet<T> set = new LinkedHashSet<>(1);
        set.add(obj);
        return set;
    }
}
