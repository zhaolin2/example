/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geekbang.flink.dynamicrules.sinks;

import static com.geekbang.flink.config.Parameters.GCP_PROJECT_NAME;
import static com.geekbang.flink.config.Parameters.GCP_PUBSUB_RULES_SUBSCRIPTION;
import static com.geekbang.flink.config.Parameters.RULES_EXPORT_SINK;
import static com.geekbang.flink.config.Parameters.RULES_EXPORT_TOPIC;

import com.geekbang.flink.config.Config;
import com.geekbang.flink.dynamicrules.KafkaUtils;
import com.geekbang.flink.dynamicrules.Rule;
import com.geekbang.flink.dynamicrules.functions.JsonSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.gcp.pubsub.PubSubSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

@Slf4j
public class CurrentRulesSink {

  public static SinkFunction<String> createRulesSink(Config config) throws IOException {

    String sinkType = config.get(RULES_EXPORT_SINK);
    CurrentRulesSink.Type currentRulesSinkType =
        CurrentRulesSink.Type.valueOf(sinkType.toUpperCase());

    switch (currentRulesSinkType) {
      case KAFKA:
        Properties kafkaProps = KafkaUtils.initProducerProperties(config);
        String alertsTopic = config.get(RULES_EXPORT_TOPIC);
        return new FlinkKafkaProducer<>(alertsTopic, new SimpleStringSchema(), kafkaProps);
      case PUBSUB:
        return PubSubSink.<String>newBuilder()
            .withSerializationSchema(new SimpleStringSchema())
            .withProjectName(config.get(GCP_PROJECT_NAME))
            .withTopicName(config.get(GCP_PUBSUB_RULES_SUBSCRIPTION))
            .build();
      case STDOUT:
        return new PrintSinkFunction<>(true);
      default:
        throw new IllegalArgumentException(
            "Source \""
                + currentRulesSinkType
                + "\" unknown. Known values are:"
                + Arrays.toString(Type.values()));
    }
  }

  public static DataStream<String> rulesStreamToJson(DataStream<Rule> alerts) {
    return alerts.flatMap(new JsonSerializer<>(Rule.class, log)).name("Rules Serialization");
  }

  public enum Type {
    KAFKA("Alerts Sink (Kafka)"),
    PUBSUB("Alerts Sink (Pub/Sub)"),
    STDOUT("Alerts Sink (Std. Out)");

    private String name;

    Type(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
