package com.geekbang.recommend.task.offline;

import com.geekbang.recommend.util.Property;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.connector.jdbc.JdbcInputFormat;
import org.apache.flink.types.Row;

public class ContentRecommender {
    public static void contentRecommend() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // mysql datasource
        TypeInformation[] fieldTypes = new TypeInformation[] {
                BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO
        };
        RowTypeInfo rowTypeInfo = new RowTypeInfo(BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO);
        JdbcInputFormat jdbcInputFormat = JdbcInputFormat.buildJdbcInputFormat()
                .setDrivername(new String(Property.getStrValue("mysql.driver")))
                .setDBUrl(new String(Property.getStrValue("mysql.url")))
                .setUsername(new String(Property.getStrValue("mysql.username")))
                .setPassword(new String(Property.getStrValue("mysql.password")))
                .setQuery("select productId, name, tags from product")
                .setRowTypeInfo(rowTypeInfo)
                .finish();
        DataSet dataSet = env.createInput(jdbcInputFormat, rowTypeInfo);
        dataSet.map(new MapFunction<Row, Row>() {
            @Override
            public Row map(Row r) throws Exception {
                String s = r.getField(2).toString().replace("|", " ");
                return  Row.of(r.getField(0), r.getField(1), s);
            }
        }).print();
        // TODO 计算 tf-idf
        env.execute();
    }
    public static void main(String[] args) throws Exception {
        contentRecommend();
    }
}
