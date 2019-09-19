package cn.houlinan.mylife.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Title: 数据分发中心 <BR>
 * Description: 阿里  druid 连接池监控<BR>
 * TODO <BR>
 * Copyright: Copyright (c) 2004-2017 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 *
 * @author liu.jian
 * @version 1.0
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = ""),// IP白名单(没有配置或者为空，则允许所有访问)
                @WebInitParam(name = "deny", value = "192.168.1.73"),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name = "loginUsername", value = "liujian"),// 用户名
                @WebInitParam(name = "loginPassword", value = "Trsadmin!@#123"),// 密码
                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
        })
public class DruidStatViewServlet extends StatViewServlet {
    /**
     *
     */
    private static final long serialVersionUID = -4100993568907583299L;

}
