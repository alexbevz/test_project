package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.ConvertibleCollectionImpl;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvConverterTests {
    protected CsvConverter csvConverter = new CsvConverter();
    protected ByteArrayOutputStream os = new ByteArrayOutputStream();

    @Test(expected = NullPointerException.class)
    public void convertTestWhenMapIsEmpty() throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        data.add(new HashMap<>());

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);

        csvConverter.convert(convertibleCollection, os);
    }

    @Test
    public void convertTestWhenMapContainsEntryWithValueIsNull() throws IOException {
        byte[] expected = "key1\n".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("key1", null);
        data.add(map);
        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);

        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertTestWhenMapContainsEntryWithKeyIsNull() throws IOException {
        byte[] expected = "val1".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put(null, "val1");
        data.add(map);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertTestWhenMapWithOneCell() throws IOException {
        byte[] expected = "key1\nval1".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("key1", "val1");
        data.add(map);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);

        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }
    @Test
    public void convertTestWhenMatrix2x2() throws IOException {
        byte[] expected = "key1,key2\nval1,val2\nval3,val4".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        map1.put("key2", "val2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "val3");
        map2.put("key2", "val4");

        data.add(map1);
        data.add(map2);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);

        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertTestWhenMatrixWithValueOnDiagonal() throws IOException {
        byte[] expected = "key1,key2,key3\nval1,,\n,val2,\n,,val3".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key2", "val2");
        Map<String, String> map3 = new HashMap<>();
        map3.put("key3", "val3");

        data.add(map1);
        data.add(map2);
        data.add(map3);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);

        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertTestWhenDataContainsOneColumn() throws IOException {
        byte[] expected = "key1\nval1\nval2\nval3".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "val2");
        Map<String, String> map3 = new HashMap<>();
        map3.put("key1", "val3");

        data.add(map1);
        data.add(map2);
        data.add(map3);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);

        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertTestWhenDataContainsOneRow() throws IOException {
        byte[] expected = "key1,key2,key3\nval1,val2,val3".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        map1.put("key2", "val2");
        map1.put("key3", "val3");
        data.add(map1);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertTestWhenNoStructure() throws IOException {
        byte[] expected = "key1,key2\nval1,\nval3,val4".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "val3");
        map2.put("key2", "val4");
        data.add(map1);
        data.add(map2);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(result, expected);
    }

    @Test
    public void convertTestWhenHaveDoubleQuote() throws IOException {
        byte[] expected = "key1,\"ke\"\"y2\"\n\"va\"\"l1\",val2\nval3,val4".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "va\"l1");
        map1.put("ke\"y2", "val2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "val3");
        map2.put("ke\"y2", "val4");
        data.add(map1);
        data.add(map2);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(result, expected);
    }

    @Test
    public void convertTestWhenContainsMetaSymbolInValues() throws IOException {
        byte[] expected = "key1,key2\nval1,\nval3,val4".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "va\nl1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "va\rl3");
        map2.put("key2", "val4");
        data.add(map1);
        data.add(map2);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(result, expected);
    }

    @Test
    public void convertTestWhenContainsMetaSymbolInKeys() throws IOException {
        byte[] expected = "key1,key2\nval1,val2\nval3,val4".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        map1.put("ke\ny2", "val2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "val3");
        map2.put("ke\ny2", "val4");
        data.add(map1);
        data.add(map2);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertTestWhenHaveMetaAndSpecSymbols() throws IOException {
        byte[] expected = "key1,\"ke\"\"y2\"\nval1,val2\nval3,val4".getBytes();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        map1.put("ke\"\ny2", "val2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "val3");
        map2.put("ke\"\ny2", "val4");
        data.add(map1);
        data.add(map2);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
        byte[] result = os.toByteArray();

        Assert.assertArrayEquals(expected, result);
    }

    @Test(expected = NullPointerException.class)
    public void convertTestWhenListIsEmpty() throws IOException {
        ConvertibleCollection convertibleCollection =
                new ConvertibleCollectionImpl(new ArrayList<>());

        csvConverter.convert(convertibleCollection, os);

    }

    @Test(expected = NullPointerException.class)
    public void convertTestWhenListIsNull() throws IOException {
        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(null);

        csvConverter.convert(convertibleCollection, os);
    }

    @Test(expected = NullPointerException.class)
    public void convertTestWhenMapIsNull() throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        data.add(null);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(data);
        csvConverter.convert(convertibleCollection, os);
    }

}