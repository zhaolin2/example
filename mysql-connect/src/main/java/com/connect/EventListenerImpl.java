package com.connect;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.google.common.collect.MoreCollectors;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import static com.connect.BinLogUtils.getdbTable;

public class EventListenerImpl implements BinaryLogClient.EventListener {

//    @Option(name = "-binlog-consume_threads", usage = "the thread num of consumer")
    private int consumerThreads = 4;

    private BinaryLogClient parseClient;

    private BlockingQueue<BinLogItem> queue;
    private final ExecutorService consumer = MoreExecutors.newDirectExecutorService();

    // 存放每张数据表对应的listener
    private Multimap<String, BinaryLogClient.EventListener> listeners;

    private Map<String, Map<String, Column>> dbTableCols;
    private String dbTable;

    /**
     * 监听处理
     *
     * @param event
     */
    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();

        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData tableData = event.getData();
            String db = tableData.getDatabase();
            String table = tableData.getTable();
            dbTable = getdbTable(db, table);
        }

        // 只处理添加删除更新三种操作
        if (EventType.isWrite(eventType) || EventType.isUpdate(eventType) || EventType.isDelete(eventType)) {
            if (EventType.isWrite(eventType)) {
                WriteRowsEventData data = event.getData();
                for (Serializable[] row : data.getRows()) {
                    if (dbTableCols.containsKey(dbTable)) {
                        BinLogItem item = BinLogItem.itemFromInsertOrDeleted(row, dbTableCols.get(dbTable), eventType);
                        item.setDbTable(dbTable);
                        queue.add(item);
                    }
                }
            }
            if (EventType.isUpdate(eventType)) {
                UpdateRowsEventData data = event.getData();
                for (Map.Entry<Serializable[], Serializable[]> row : data.getRows()) {
                    if (dbTableCols.containsKey(dbTable)) {
                        BinLogItem item = BinLogItem.itemFromUpdate(row, dbTableCols.get(dbTable), eventType);
                        item.setDbTable(dbTable);
                        queue.add(item);
                    }
                }

            }
            if (EventType.isDelete(eventType)) {
                DeleteRowsEventData data = event.getData();
                for (Serializable[] row : data.getRows()) {
                    if (dbTableCols.containsKey(dbTable)) {
                        BinLogItem item = BinLogItem.itemFromInsertOrDeleted(row, dbTableCols.get(dbTable), eventType);
                        item.setDbTable(dbTable);
                        queue.add(item);
                    }
                }
            }
        }
    }

    /**
     * 注册监听
     *
     * @param db       数据库
     * @param table    操作表
     * @param listener 监听器
     * @throws Exception
     */
    public void regListener(String db, String table, BinaryLogClient.EventListener listener) throws Exception {
        String dbTable = getdbTable(db, table);
        // 获取字段集合
//        Map<String, Column> cols = BinLogUtils.getColMap(conf, db, table);
        // 保存字段信息
//        dbTableCols.put(dbTable, cols);
        // 保存当前注册的listener
        listeners.put(dbTable, listener);
    }

    /**
     * 开启多线程消费
     *
     * @throws IOException
     */
    public void parse() throws IOException {
        parseClient.registerEventListener(this);

        for (int i = 0; i < consumerThreads; i++) {
            consumer.submit(() -> {
                while (true) {
                    if (queue.size() > 0) {
                        try {
                            BinLogItem item = queue.take();
                            String dbtable = item.getDbTable();
//                            listeners.get(dbtable).forEach(binLogListener -> binLogListener.onEvent(item.getEventType()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    Thread.sleep(BinLogConstants.queueSleep);
                    Thread.sleep(100);

                }
            });
        }
        parseClient.connect();
    }
}
