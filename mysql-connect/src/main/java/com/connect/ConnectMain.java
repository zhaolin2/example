package com.connect;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.google.common.collect.Collections2;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ConnectMain {
    public static void main(String[] args) throws IOException {

        Conf conf = new Conf();
        conf.setHost("127.0.0.1");
        conf.setPort(3306);
        conf.setUsername("root");
        conf.setPasswd("root");

        String dbName="sinozo_advertsing";
        String tableName="sys_dict_data";

        EventListenerImpl mysqlBinLogListener =  new EventListenerImpl(conf);

        List<String> tableList = BinLogUtils.getListByStr(tableName);
        if (tableList.isEmpty()) {
            return;
        }
        // 注册监听
        tableList.forEach(table -> {
            System.out.println("注册监听信息，注册DB：" + dbName + "，注册表：" + table);
            try {
                mysqlBinLogListener.regListener(dbName, table, item -> {
                    System.out.println("监听逻辑处理");
                });
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("BinLog监听异常：" + e);
            }
        });
        // 多线程消费
        mysqlBinLogListener.parse();
//        BinaryLogClient client = new BinaryLogClient(conf.getHost(), conf.getPort(), conf.getUsername(), conf.getPasswd());
//        EventDeserializer eventDeserializer = new EventDeserializer();
//        eventDeserializer.setCompatibilityMode(
//                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
//        );
//        client.setEventDeserializer(eventDeserializer);
//        client.registerEventListener(new EventListenerImpl());
//        try {
//            client.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("连接错误,"+e.getMessage());
//        }
    }
}
