package cn.houlinan.mylife.service;

import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.handler.NumberHandler;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.JdbcType;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.Collection;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/17
 * Time : 15:04
 */
@Service
@Slf4j
public class SqlService {

    DruidDataSource ds = null ;

    Connection conn = null;

    @PostConstruct
    public void init()throws Exception{
        Props props = new Props("application.properties");
        String db_url = props.getStr("spring.datasource.primary.url");
        String db_userName = props.getStr("spring.datasource.primary.username");
        String db_password = props.getStr("spring.datasource.primary.password");

        ds = new DruidDataSource();
        ds.setUrl(db_url);
        ds.setUsername(db_userName);
        ds.setPassword(db_password);
        conn  = ds.getConnection();
    }

    public JSONArray execute(String sql , String... params)throws Exception{
        return JSONArray.fromObject(SqlExecutor.query(conn  , sql , new EntityListHandler() , params)) ;
    }

    public Integer quertCountBySql(String sql )throws Exception{
        Number query = SqlExecutor.query(conn, sql, new NumberHandler());
        return  query.intValue();
    }


}
