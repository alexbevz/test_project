package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import static org.eagleinvsys.test.converters.constants.MyConstants.*;
import static org.eagleinvsys.test.converters.utils.MyUtils.escapeSpecialCharacters;

public class CsvConverter implements Converter {

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) throws IOException {
        if (collectionToConvert == null || outputStream == null || collectionToConvert.getHeaders().isEmpty()) {
            throw new NullPointerException();
        }

        String header = null;
        for (Iterator<String> ic = collectionToConvert.getHeaders().iterator();
             ic.hasNext(); ) {
            header = ic.next();
            outputStream.write(
                    (
                            escapeSpecialCharacters(header != null ? header : "", SEP) + (ic.hasNext() ? SEP : "")
                    ).getBytes());

        }
        if (!(collectionToConvert.getHeaders().size() == 1 && header == null)) {
            outputStream.write(END.getBytes());
        }

        for (Iterator<ConvertibleMessage> icm = collectionToConvert.getRecords().iterator();
             icm.hasNext(); ) {
            ConvertibleMessage cm = icm.next();
            for (Iterator<String> icc = collectionToConvert.getHeaders().iterator();
                 icc.hasNext(); ) {
                String curVal = cm.getElement(icc.next());
                outputStream.write(
                        (
                                escapeSpecialCharacters(curVal != null ? curVal : "", SEP)
                                        + (icc.hasNext() ? SEP : "")
                        ).getBytes()
                );
            }
            if (icm.hasNext()) outputStream.write(END.getBytes());
        }
    }
}