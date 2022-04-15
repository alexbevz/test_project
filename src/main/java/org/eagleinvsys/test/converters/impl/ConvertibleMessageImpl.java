package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.Map;

public class ConvertibleMessageImpl implements ConvertibleMessage {
    private final Map<String, String> elements;

    public ConvertibleMessageImpl(Map<String, String> elements) {
        this.elements = elements;
    }

    @Override
    public String getElement(String elementId) {
        return elements.get(elementId);
    }
}