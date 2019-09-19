package cn.houlinan.mylife.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Title: 数据分发中心 <BR>
 * Description: 阿里druid 数据源配置，spring默认不支持，需要自行配置<BR>
 * TODO <BR>
 * Copyright: Copyright (c) 2004-2017 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 *
 * @author liu.jian
 * @version 1.0
 */
@EnableTransactionManagement
@Configuration
@Component
public class DruidDBConfig {
    private static Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);
    @Value("${spring.datasource.primary.url}")
    private String dbUrlPrimary;

    @Value("${spring.datasource.primary.username}")
    private String usernamePrimary;

    @Value("${spring.datasource.primary.password}")
    private String passwordPrimary;

//    @Value("${spring.datasource.second.username}")
//    private String usernameSecond;
//
//    @Value("${spring.datasource.second.password}")
//    private String passwordSecond;
//
//    @Value("${spring.datasource.second.url}")
//    private String dbUrlSecond;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

//    @Value("${spring.datasource.initialSize}")
//    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

//    @Value("${spring.datasource.maxActive}")
//    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    /**
     * 打开PSCache，并且指定每个连接上PSCache的大小
     */
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
     */
    @Value("${spring.datasource.filters}")
    private String filters;
    /**
     * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
     */
    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;


    @Value("${spring.datasource.logAbandoned}")
    private boolean logAbandoned;

    @Value("${spring.datasource.removeAbandoned}")
    private boolean removeAbandoned;

    @Value("${spring.datasource.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;

    @Bean(name = "primaryDataSource")    //声明其为Bean实例
    @Qualifier("primaryDataSource")
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DruidDataSource primaryDataSource() {
        return getDruidDataSource(usernamePrimary, passwordPrimary, dbUrlPrimary);
    }

    @Bean(name = "PrimaryJdbcTemplate")
    public JdbcTemplate baseJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
//
//    @Bean(name = "secondDataSource")    //声明其为Bean实例
//    @Qualifier("secondDataSource")
//    public DruidDataSource seconddataSource() {
//        return getDruidDataSource(usernameSecond, passwordSecond, dbUrlSecond);
//    }
//
//    @Bean(name = "secondJdbcTemplate")
//    public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

    private DruidDataSource getDruidDataSource(String username, String password, String url) {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration  
//        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setLogAbandoned(logAbandoned);
        datasource.setRemoveAbandoned(removeAbandoned);
        datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter : {0}", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }
}

