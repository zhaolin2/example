package com.flink.dataStream.etl;

import com.constants.KafkaConstants;
import com.flink.dataStream.sink.RichCaseSink;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.logging.Level;

public class KafkaToMysql {
    public static void main(String[] args) throws Exception {
        LocalStreamEnvironment environment = StreamExecutionEnvironment.createLocalEnvironment();

        SimpleStringSchema schema = new SimpleStringSchema();

        KafkaSource<String> source = KafkaSource.<String>builder().setBootstrapServers(KafkaConstants.KAFKA_BOOTSTRAP_SERVER)
                .setTopics(KafkaConstants.TEST_TOPIC)
                .setGroupId(KafkaConstants.CONSUMER_GROUP)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(schema)
                .build();

        DataStreamSource<String> dataStreamSource = environment.fromSource(source, WatermarkStrategy.noWatermarks(), "kafka-source");

        dataStreamSource.addSink(new RichCaseSink());


        environment.execute("kakf-mysql");
    }
}
