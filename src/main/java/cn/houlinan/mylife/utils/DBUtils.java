package cn.houlinan.mylife.utils;

import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.JdbcType;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.util.Collection;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 9:09
 */
@Slf4j
public class DBUtils {


    public static String main(String[] args)throws Exception {
        DruidDataSource ds  = new DruidDataSource();
        ds.setUrl("jdbc:mysql://139.199.31.168:3306/mylife?useUnicode=true&characterEncoding=utf-8&useSSL=false&tinyInt1isBit=false&serverTimezone=GMT%2b8");
        ds.setUsername("root");
        ds.setPassword("f6vxg29gkhI");
        Connection conn = ds.getConnection();

        Table commment = MetaUtil.getTableMeta(ds, "comment");

        log.info(JSONObject.fromObject(commment).toString());


        Collection<Column> columns = commment.getColumns();
        StringBuffer result =  new StringBuffer("\n");

        columns.stream().forEach(a ->{

            if(!CMyString.isEmpty(a.getComment())){
                return  ;
            }
            if("id".equals(a.getName())) return ;
//            log.info(JSONObject.fromObject(a).toString());
            String s = a.getTypeName() ;
            result.append("ALTER TABLE " + a.getTableName() + " MODIFY COLUMN `" + a.getName() + "` " + s );
            if(a.getType() != JdbcType.TIMESTAMP.typeCode){
                result.append("(" + a.getSize()  +")");
            }
            if(!JSONObject.fromObject(a).getBoolean("nullable")){
                result.append(" NOT NULL ");
            }
            result.append( " COMMENT ''; \n");
        });
//        System.out.println(SqlExecutor.query(conn,  "select count(1) from `comment` where id = '123123123';"  , new NumberHandler()));
        log.info(result.toString());
        return result.toString();
    }

}
