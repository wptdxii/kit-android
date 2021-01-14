package com.rocbillow.kia;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;

/**
 * @author rocbillow
 * @date 2021-01-09
 */
public class Test {

}

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("42")
        CollectionsKt.forEach(list, s -> {
            System.out.println(s);
            return Unit.INSTANCE
        });
    }

