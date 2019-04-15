package com.yasenagat.zkweb.util;

import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   
 *  * Copyright: Copyright (c) 2019 Asiainfo
 *  * 
 *  * @ClassName: com.yasenagat.zkweb.util.ConnUtil
 *  * @Description: 该类的功能描述
 *  *
 *  * @version: v1.0.0
 *  * @author: wenghy   
 *  * @date: 2019/4/15 9:53 AM 
 *  *
 *  * Modification History:
 *  * Date         Author          Version            Description
 *  *------------------------------------------------------------
 *  * 2019/4/15      wenghy          v1.0.0               修改原因
 */
public class ConnUtil {
    private static Logger log = LoggerFactory.getLogger(ConnUtil.class);
    @Autowired
    private static DataSource dataSource;
    public static DataSource getDataSource(){

        return dataSource;
    }
    public static void destroyDataSource() {
//		if(dataSource!=null)
//			dataSource.close();
    }
    public static void close(ResultSet rs , PreparedStatement ps , Connection conn){
        close(rs);
        close(ps);
        close(conn);
    }
    public static void close(ResultSet rs , PreparedStatement ps){
        close(rs);
        close(ps);
    }

    public static void close(ResultSet rs ){
        if(null != rs){
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("ResultSet close fail:",e);
            }
        }

    }

    public static void close(PreparedStatement ps ){

        if(null != ps){
            try {
                ps.close();
            } catch (SQLException e) {
                log.error("PreparedStatement close fail:",e);
            }
        }

    }

    public static void close( Connection conn){
        if(null != conn){
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("Connection close fail:",e);
            }
        }
    }
    public static ResultSetHandler<Object[]> objectHandler = new ResultSetHandler<Object[]>() {
        public Object[] handle(ResultSet rs) throws SQLException {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            Object[] result = new Object[cols];

            for (int i = 0; i < cols; i++) {
                result[i] = rs.getObject(i + 1);
            }

            return result;
        }
    };

    public static ResultSetHandler<Map<String, Object>> mapHandler = new ResultSetHandler<Map<String,Object>>() {

        public Map<String, Object> handle(ResultSet rs) throws SQLException {
            Map<String, Object> map = new HashMap<String, Object>();
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            for(int i = 0 ; i < cols ;i++){
                map.put(meta.getColumnName(i+1), rs.getObject(i+1));
            }
            return map;
        }
    };

    public static ResultSetHandler<Integer> intHandler = new ResultSetHandler<Integer>() {

        public Integer handle(ResultSet rs) throws SQLException {

            return rs.getInt(1);
        }


    };

    public static ResultSetHandler<List<Map<String, Object>>> ListHandler = new ResultSetHandler<List<Map<String, Object>>>() {

        public List<Map<String, Object>> handle(ResultSet rs)
                throws SQLException {


            List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

            ResultSetMetaData meta = rs.getMetaData();
            Map<String, Object> map = null;
            int cols = meta.getColumnCount();
            while(rs.next()){
                map = new HashMap<String, Object>();
                for(int i = 0 ; i < cols ;i++){
                    map.put(meta.getColumnName(i+1), rs.getObject(i+1));
                }
                list.add(map);
            }

            return list;
        }
    };
}
