package com.flink.dataStream.sink;

import com.flink.dataStream.etl.T1;
import com.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class RichCaseSink extends RichSinkFunction<String> {
    private static final String UPSERT_CASE = "INSERT INTO demo.t1 (id, money,time) "
            + "VALUES (?, ?,?) ";

    private PreparedStatement statement;


    @Override
    public void invoke(String value) throws Exception {

        try {
            T1 t = JsonUtils.toEntity(value, T1.class);

            statement.setString(1, String.valueOf(t.getId()));
            statement.setString(2, String.valueOf(t.getMoney()));
            statement.setString(3, String.valueOf(t.getTime()));
            statement.addBatch();
            statement.executeBatch();
            log.info("保存成功,[value:{}]",value);
        } catch (Exception throwables) {
            log.info("保存错误,[value:{}]",value,throwables);
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?user=root&password=mysql_nrhhQf");


        statement = connection.prepareStatement(UPSERT_CASE);
    }
}
