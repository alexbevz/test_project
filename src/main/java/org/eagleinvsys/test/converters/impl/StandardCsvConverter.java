package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleMessage;
import org.eagleinvsys.test.converters.StandardConverter;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class StandardCsvConverter implements StandardConverter {

    private final CsvConverter csvConverter;

    public StandardCsvConverter(CsvConverter csvConverter) {
        this.csvConverter = csvConverter;
    }

    /**
     * Converts given {@link List<Map>} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format. All maps must have the same set of keys
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(List<Map<String, String>> collectionToConvert, OutputStream outputStream) throws IOException {
        if (collectionToConvert == null || outputStream == null || collectionToConvert.isEmpty()) {
            throw new NullPointerException();
        }
        Map<String, String> firstMap = collectionToConvert.get(0);

        if (firstMap == null) {
            throw new NullPointerException();
        }
        Set<String> setKeys = firstMap.keySet();
        //TODO: to need to optimize
        for (Set<String> set : collectionToConvert.stream().map(Map::keySet).collect(Collectors.toSet())) {
            if (!set.equals(setKeys)) {
                throw new IllegalArgumentException();
            }
        }

        ConvertibleCollectionImpl conCol = new ConvertibleCollectionImpl(collectionToConvert);

        csvConverter.convert(conCol, outputStream);
    }

}