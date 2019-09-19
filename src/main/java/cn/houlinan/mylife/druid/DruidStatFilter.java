package cn.houlinan.mylife.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Title: 数据分发中心 <BR>
 * Description: 阿里druid 连接池拦截器<BR>
 * TODO <BR>
 * Copyright: Copyright (c) 2004-2017 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 *
 * @author liu.jian
 * @version 1.0
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),//忽略资源
                @WebInitParam(name = "sessionStatEnable", value = "false")//关闭session监控
        }
)
public class DruidStatFilter extends WebStatFilter {

}
