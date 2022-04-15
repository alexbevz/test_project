package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.*;
import java.util.stream.Collectors;

public class ConvertibleCollectionImpl implements ConvertibleCollection {
    private final Collection<String> headers;
    private final Iterable<ConvertibleMessage> records;

    public ConvertibleCollectionImpl(Collection<Map<String, String>> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new NullPointerException();
        }
        Set<String> set = new HashSet<>();
        try {
            collection.forEach(o -> set.addAll(o.keySet()));
        } catch (Exception e) {
            throw new NullPointerException();
        }
        this.headers = set;
        this.records = collection.stream()
                .map(ConvertibleMessageImpl::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<String> getHeaders() {
        return this.headers;
    }

    @Override
    public Iterable<ConvertibleMessage> getRecords() {
        return this.records;
    }
}
