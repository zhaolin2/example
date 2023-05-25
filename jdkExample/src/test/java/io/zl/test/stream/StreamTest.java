package io.zl.test.stream;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StreamTest {

    public void testStream() throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("123");
        arrayList.add("567");

        arrayList.stream().map(String::toUpperCase).collect(Collectors.toList());
    }
}
