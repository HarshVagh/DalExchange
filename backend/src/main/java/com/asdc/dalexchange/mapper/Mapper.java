package com.asdc.dalexchange.mapper;

public interface Mapper<A,B> {
    B mapTo(A a);
    A mapFrom(B b);
}

