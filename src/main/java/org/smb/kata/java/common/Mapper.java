package org.smb.kata.java.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public interface Mapper<S, T> {

    default T from(S source) {
        throw new UnsupportedOperationException("from(S) is not implemented");
    }

    default List<T> from(Iterable<S> sources) {
        return from(sources, (s,t) -> {});
    }

    default List<T> from(Iterable<S> sources, BiConsumer<S, T> postProcessor) {
        List<T> targetList = new ArrayList<>();
        for (S entity : sources) {
            try {
                T dto = from(entity);
                postProcessor.accept(entity, dto);
                targetList.add(dto);
            } catch (RuntimeException ex) {
                System.out.println("Cannot map, skipping");
            }
        }
        return targetList;
    }

    default void map(S source, T target) {
        throw new UnsupportedOperationException("map(S, T) is not implemented");
    }
}