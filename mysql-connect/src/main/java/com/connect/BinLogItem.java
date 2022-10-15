package com.connect;

import com.github.shyiko.mysql.binlog.event.EventType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.shyiko.mysql.binlog.event.EventType.isWrite;

public class BinLogItem {
    private static final long serialVersionUID = 5503152746318421290L;

    private String dbTable;
    private EventType eventType;
    private Long timestamp = null;
    private Long serverId = null;
    // 存储字段-之前的值之后的值
    private Map<String, Serializable> before = null;
    private Map<String, Serializable> after = null;
    // 存储字段--类型
    private Map<String, Column> colums = null;


    private Map<String, String> beforeMap = null;
    private Map<String, String> afterMap = null;

    /**
     * 新增或者删除操作数据格式化
     */
    public static BinLogItem itemFromInsertOrDeleted(Serializable[] row, Map<String, Column> columnMap, EventType eventType) {
        if (null == row || null == columnMap) {
            return null;
        }
        if (row.length != columnMap.size()) {
            return null;
        }
        // 初始化Item
        BinLogItem item = new BinLogItem();
        item.eventType = eventType;
        item.colums = columnMap;
        item.before = new HashMap<>();
        item.after = new HashMap<>();

        Map<String, Serializable> beOrAf = new HashMap<>();

        columnMap.entrySet().forEach(entry -> {
            String key = entry.getKey();
            Column colum = entry.getValue();
            beOrAf.put(key, row[colum.inx]);
        });

        // 写操作放after，删操作放before
        if (isWrite(eventType)) {
            item.after = beOrAf;
        }
        if (EventType.isDelete(eventType)) {
            item.before = beOrAf;
        }

        return item;
    }

    /**
     * 更新操作数据格式化
     */
    public static BinLogItem itemFromUpdate(Map.Entry<Serializable[], Serializable[]> mapEntry, Map<String, Column> columnMap, EventType eventType) {
        if (null == mapEntry || null == columnMap) {
            return null;
        }
        // 初始化Item
        BinLogItem item = new BinLogItem();
        item.eventType = eventType;
        item.colums = columnMap;
        item.before = new HashMap<>();
        item.after = new HashMap<>();

        Map<String, Serializable> be = new HashMap<>();
        Map<String, Serializable> af = new HashMap<>();

        columnMap.entrySet().forEach(entry -> {
            String key = entry.getKey();
            Column colum = entry.getValue();
            be.put(key, mapEntry.getKey()[colum.inx]);

            af.put(key, mapEntry.getValue()[colum.inx]);
        });

        item.before = be;
        item.after = af;

        Map<String, String> beforeMap = item.before.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), serialToString(entry.getValue())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        item.beforeMap=beforeMap;

        System.out.println("item.beforeMap:"+beforeMap);

        Map<String, String> afterMap = item.after.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), serialToString(entry.getValue())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        item.afterMap=afterMap;
        System.out.println("item.beforeMap:"+afterMap);

        return item;
    }

    static public String serialToString(Serializable serializable){
        if (Objects.isNull(serializable)){
            return "";
        }
        if (serializable instanceof  byte[]){
            byte[] bytes = (byte[]) serializable;
            return new String(bytes);
        }else {
            System.out.println("serialable.class:"+serializable.getClass());
            System.out.println("serialable:"+serializable);
        }
        return String.valueOf(serializable);
    }

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Map<String, Serializable> getBefore() {
        return before;
    }

    public void setBefore(Map<String, Serializable> before) {
        this.before = before;
    }

    public Map<String, Serializable> getAfter() {
        return after;
    }

    public void setAfter(Map<String, Serializable> after) {
        this.after = after;
    }

    public Map<String, Column> getColums() {
        return colums;
    }

    public void setColums(Map<String, Column> colums) {
        this.colums = colums;
    }

    @Override
    public String toString() {
        return "BinLogItem{" +
                "dbTable='" + dbTable + '\'' +
                ", eventType=" + eventType +
                ", timestamp=" + timestamp +
                ", serverId=" + serverId +
                ", before=" + before +
                ", after=" + after +
                ", colums=" + colums +
                '}';
    }
}
