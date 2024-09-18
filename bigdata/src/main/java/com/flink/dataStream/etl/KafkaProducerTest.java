package com.flink.dataStream.etl;

import com.constants.KafkaConstants;
import com.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class KafkaProducerTest {
    static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        Map<String, Object> kafkaParam = new HashMap<>(3);
        String topic = args.length == 0 ? KafkaConstants.TEST_TOPIC : args[0];
        System.out.println("produce-topic:" + topic);
        kafkaParam.put("bootstrap.servers", KafkaConstants.KAFKA_BOOTSTRAP_SERVER);
        kafkaParam.put("key.serializer", StringSerializer.class.getName());
        kafkaParam.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaParam);
        int index = 0;
        while (index < 100000) {
            String msg = genMessage();
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);
            kafkaProducer.send(record,(result,ex)->{
                if (Objects.nonNull(ex)){
                    log.info("发送错误,message:{}",record,ex);
                }else {
                    log.info("发送成功,message:{}",record);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("index:{}",index);
            index++;
        }
        kafkaProducer.close();
    }

    /**
     * 构造消息
     * @return
     */
    public static String genMessage() {

        T1 build = T1.builder()
                .id(RandomUtils.nextInt())
                .money(RandomUtils.nextInt())
                .time(LocalDateTime.now())
                .build();
        String str = JsonUtils.toString(build);
        System.out.println(str);
        return str;
    }


}
