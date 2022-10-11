package com.connect;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.IOException;

public class ConnectMain {
    public static void main(String[] args) {

        Conf conf = new Conf();
        conf.setHost("127.0.0.1");
        conf.setPort(3306);
        conf.setUsername("root");
        conf.setPasswd("root");

        BinaryLogClient client = new BinaryLogClient(conf.getHost(), conf.getPort(), conf.getUsername(), conf.getPasswd());
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(new EventListenerImpl());
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("连接错误,"+e.getMessage());
        }
    }
}
