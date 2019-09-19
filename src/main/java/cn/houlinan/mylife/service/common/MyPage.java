/**
 *
 */
package cn.houlinan.mylife.service.common;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Title: <BR>
 * Description: <BR>
 * TODO <BR>
 * Copyright: Copyright (c) 2004-2017 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 *
 * @author zhang.ping

 * @version 1.0

 */
@Data
public class MyPage<T> {
    private int page;//从0开始,第几页
    private int maxpage;//允许最大的页数
    private int size = 10;//每页条数 默认10
    private int totalPages;//总页数
    private int totalElements = 0;//总记录数
    private String sort = "";//如：crtime,desc(普通sql的空格用逗号代替)
    private List<T> content;//列表数据

    public MyPage() {

    }

    /**
     * 构造函数，获取总页数，最大允许页数
     * @param totalNum
     * @param pageSize
     * @throws Exception
     */
    public MyPage(int totalNum, int pageSize) throws Exception {
        if (totalNum < 0 || pageSize < 1) {
            throw new Exception("初始化分页对象参数错误");
        }
        if (pageSize > 1000) {
            this.size = 1000;
        } else {
            this.size = pageSize;
        }
        this.totalElements = totalNum;

        if (totalNum == 0) {
            totalPages = 0;
            this.maxpage = 0;
        }
        if (totalNum >= 1 && totalNum <= pageSize) {
            totalPages = 1;
            this.maxpage = 0;
        }
        if (totalNum > pageSize) {
            if ((totalNum % pageSize) == 0) {
                totalPages = totalNum / pageSize;
                this.maxpage = totalPages - 1;
            } else {
                totalPages = totalNum / pageSize + 1;
                this.maxpage = totalPages - 1;
            }
        }
    }

    /**
     *
     * Description:  传入第几页获取起始index<BR>
     * @author zhang.ping

     * @date 2017年11月07日 下午4:29:14

     * @param pageindex
     * @return
     */
    public int getStartIndex(int pageindex) {
        if (pageindex >= this.maxpage) {
            return this.maxpage * this.size;
        }
        if (pageindex < this.maxpage) {
            return pageindex * this.size;
        }
        return 0;
    }

    /**
     *
     * Description:   传入第几页获取结束index<BR> <BR>
     * @author zhang.ping

     * @date 2017年11月07日 下午4:29:40

     * @param pageindex
     * @return
     */
    public int getEndIndex(int pageindex) {
        if (pageindex >= this.maxpage) {
            if (this.totalElements == 0) {
                return 0;
            } else {
                return this.totalElements - 1;
            }
        }
        if (pageindex < this.maxpage) {
            return (pageindex + 1) * this.size - 1;
        }
        return 0;
    }


    public void setTotalnum(int totalNum) throws Exception {
        if (totalNum < 0) {
            throw new Exception("totalNum不能小于零");
        }
        this.totalElements = totalNum;
        if (totalNum == 0) {
            totalPages = 0;
            this.maxpage = 0;
        }
        if (totalNum >= 1 && totalNum <= this.size) {
            totalPages = 1;
            this.maxpage = 0;
        }
        if (totalNum > this.size) {
            if ((totalNum % this.size) == 0) {
                totalPages = totalNum / this.size;
                this.maxpage = totalPages - 1;
            } else {
                totalPages = totalNum / this.size + 1;
                this.maxpage = totalPages - 1;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        @SuppressWarnings("rawtypes")
        MyPage<Map> myPage = new MyPage<Map>(100, 10);
        System.out.println("totalpage=" + myPage.getTotalPages());
        System.out.println("Maxpageindex=" + myPage.getMaxpage());
        System.out.println("StartIndex=" + myPage.getStartIndex(2));
        System.out.println("EndIndex=" + myPage.getEndIndex(2));
    }
}
