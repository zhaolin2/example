package com.connect;

import com.github.shyiko.mysql.binlog.event.EventType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static com.github.shyiko.mysql.binlog.event.EventType.isUpdate;

public class BinLogUtils {

//    private SearchStoreLogoExtMapper searchStoreLogoExtMapper;

    public void init() {
//        binLogUtils = this;
//        binLogUtils.searchStoreLogoExtMapper = this.searchStoreLogoExtMapper;
    }

    /**
     * 拼接dbTable
     */
    public static String getdbTable(String db, String table) {
        return db + "-" + table;
    }

    /**
     * 获取columns集合
     */
    public static Map<String, Column> getColMap(Conf conf, String db, String table) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 保存当前注册的表的colum信息
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + conf.getHost() + ":" + conf.getPort(), conf.getUsername(), conf.getPasswd());
            // 执行sql
            String preSql = "SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, ORDINAL_POSITION FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? and TABLE_NAME = ?";
            PreparedStatement ps = connection.prepareStatement(preSql);
            ps.setString(1, db);
            ps.setString(2, table);
            ResultSet rs = ps.executeQuery();
            Map<String, Column> map = new HashMap<>(rs.getRow());
            while (rs.next()) {
                String schema = rs.getString("TABLE_SCHEMA");
                String tableName = rs.getString("TABLE_NAME");
                String column = rs.getString("COLUMN_NAME");
                int idx = rs.getInt("ORDINAL_POSITION");
                String dataType = rs.getString("DATA_TYPE");
                if (column != null && idx >= 1) {
                    map.put(column, new Column(schema, tableName, idx - 1, column, dataType)); // sql的位置从1开始
                }
            }
            ps.close();
            rs.close();
            return map;
        } catch (SQLException e) {
            System.out.println("load db conf error, db_table={}:{}"+db+table+e);
        }
        return null;
    }

    /**
     * 根据table获取code
     *
     * @param table
     * @return java.lang.Integer
     */
    public static Integer getCodeByTable(String table) {
        if (Objects.isNull(table)) {
            return null;
        }
//        return CategoryEnum.getCodeByTab(table);
        return 0;
    }

    public static String getMsgByTab(String table) {
        if (StringUtils.isEmpty(table)) {
            return null;
        }
//        return CategoryEnum.getMsgByTab(table);
        return table;
    }

    /**
     * 根据DBTable获取table
     *
     * @param dbTable
     * @return java.lang.String
     */
    public static String getTable(String dbTable) {
        if (StringUtils.isEmpty(dbTable)) {
            return "";
        }
        String[] split = dbTable.split("-");
        if (split.length == 2) {
            return split[1];
        }
        return "";
    }

    /**
     * 将逗号拼接字符串转List
     *
     * @param str
     * @return
     */
    public static List<String> getListByStr(String str) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>();
        }

        return Arrays.asList(str.split(","));
    }

    /**
     * 根据操作类型获取对应集合
     *
     * @param binLogItem
     * @return
     */
    public static Map<String, Serializable> getOptMap(BinLogItem binLogItem) {
        // 获取操作类型
        EventType eventType = binLogItem.getEventType();
        if (EventType.isWrite(eventType) || isUpdate(eventType)) {
            return binLogItem.getAfter();
        }
        if (EventType.isDelete(eventType)) {
            return binLogItem.getBefore();
        }
        return null;
    }

    /**
     * 获取操作类型
     *
     * @param binLogItem
     * @return
     */
    public static Integer getOptType(BinLogItem binLogItem) {
        // 获取操作类型
        EventType eventType = binLogItem.getEventType();
        if (EventType.isWrite(eventType)) {
            return 1;
        }
        if (isUpdate(eventType)) {
            return 2;
        }
        if (EventType.isDelete(eventType)) {
            return 3;
        }
        return null;
    }


    /**
     * 根据storeId获取imgUrl
     */
    public static String getImgUrl(Long storeId) {

        if (storeId == null) {
            return "";
        }
        //获取url
//        SearchStoreLogo searchStoreLogo = new SearchStoreLogo();
//        searchStoreLogo.setStoreId(storeId);
//        List<SearchStoreLogo> searchStoreLogos = binLogUtils.searchStoreLogoExtMapper.selectList(searchStoreLogo);
//        if (CollectionUtil.isNotEmpty(searchStoreLogos)) {
//            SearchStoreLogo storeLogo = searchStoreLogos.get(0);
//            if (storeLogo != null) {
//                return storeLogo.getStoreLogo();
//            }
//        }
        return "";
    }

    /**
     * 格式化date
     *
     * @param date
     * @return java.util.Date
     */
    public static Date getDateFormat(Date date) {
        if (date == null) {
            return null;
        }
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String strDate = simpleDateFormat.format(date);
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }

        Date formatDate = null;
        try {
            formatDate = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }
}
