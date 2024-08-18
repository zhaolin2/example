package com.flink.processFunction;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.util.Collector;

public class MyProcessWindowsFunction extends ProcessWindowFunction {
    @Override
    public void process(Object o, Context context, Iterable elements, Collector out) throws Exception {

    }
}
