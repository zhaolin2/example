package com.flink.processFunction;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class MyProcessFunction extends ProcessFunction {
    @Override
    public void processElement(Object value, Context ctx, Collector out) throws Exception {

    }
}
