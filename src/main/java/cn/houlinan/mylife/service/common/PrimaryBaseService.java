package cn.houlinan.mylife.service.common;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Service
@Transactional
public class PrimaryBaseService {
    @Autowired //TODO 直销的地方
    @PersistenceContext(unitName = "primaryPersistenceUnit")
    @Qualifier("entityManagerPrimary")
    private EntityManager entityManager;

    /**
     * @param sql
     * @param parameters 参数键值对集合，eg：SELECT c FROM country c WHERE c.name = ?1 Map<String,
     *                   Object> parameters = new Map<String, Object>();
     *                   parameters.put("1","sfsdf");
     * @return
     * @throws @Author：liujian 2018年3月14日 上午10:46:40
     * @Title: queryCount
     * @Description: 根据原生sql查询数据的总数
     */
    public int queryCount(String sql, Map<String, Object> parameters) {
        Query query = entityManager.createNativeQuery(sql);
        if (parameters != null) {
            Set<Entry<String, Object>> set = parameters.entrySet();
            for (Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return ((BigInteger) query.getResultList().listIterator().next()).intValue();
    }

    /**
     * @param sql        查询数据的sql
     * @param parameters 参数集合
     * @param startNum   起始查询的位置 pageIndex*pageSize
     * @param pageSize   每页条数
     * @return
     * @throws @Author：liujian 2018年3月14日 上午10:47:17
     * @Title: queryData
     * @Description: 跟据原生sql查询数据
     */
    @SuppressWarnings({"unchecked"})
    public <T> List<T> queryData(String sql, Map<String, Object> parameters, int startNum, int pageSize,
                                 Class<T> tClass) {
        Query query = null;
        if (null == tClass || Map.class.equals(tClass)) {
            query = entityManager.createNativeQuery(sql);
        } else {
            query = entityManager.createNativeQuery(sql, tClass);
        }
        if (parameters != null) {
            Set<Entry<String, Object>> set = parameters.entrySet();
            for (Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        if (startNum >= 0 && pageSize >= 0) {
            query.setFirstResult(startNum);
            query.setMaxResults(pageSize);
        }
        if (null == tClass || Map.class.equals(tClass)) {
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        List<T> rows = query.getResultList();
        return rows;
    }

    /**
     * @param dataSql    查询数据的sql
     * @param countSql   查询数据总数的sql
     * @param params     参数集合
     * @param nStartPage 查询的页码
     * @param nPageSize  每页条数
     * @param tClass     转换对象的类 eg:Map.class
     * @return
     * @throws Exception
     * @throws @Author：liujian 2018年3月14日 下午4:45:21
     * @Title: executeSqlByPage
     * @Description: 根据原生sql查询数据并封装成Mypage对象返回
     */
    public <T> MyPage<T> executeSqlByPage(String dataSql, String countSql, Map<String, Object> params, int nStartPage,
                                          int nPageSize, Class<T> tClass) throws Exception {
        List<T> pageList = new ArrayList<T>();
        MyPage<T> myPage = new MyPage<T>();
        int count = this.queryCount(countSql, params);
        myPage.setSize(nPageSize);
        myPage.setTotalnum(count);
        myPage.setPage(nStartPage);
        int pageIndex = myPage.getPage();
        int pageSize = myPage.getSize();
        pageList = this.queryData(dataSql, params, pageIndex * pageSize, pageSize, tClass);
        myPage.setContent(pageList);
        return myPage;
    }

    /**
     * @param sql
     * @param parameters
     * @return
     * @throws @Author：liujian 2018年4月10日 上午10:05:59
     * @Title: queryData
     * @Description: 原生sql查询主库数据，不进行分页，返回的字段名区分大小写
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryData(String sql, Map<String, Object> parameters) {
        Query query = null;
        query = entityManager.createNativeQuery(sql);
        if (parameters != null) {
            Set<Entry<String, Object>> set = parameters.entrySet();
            for (Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> rows = query.getResultList();
        return rows;
    }

    /**
     * @param sql
     * @param parameters
     * @param tClass
     * @return
     * @throws @Author：liujian 2018年4月10日 上午10:07:36
     * @Title: queryData
     * @Description: 原生sql查询主库数据，不进行分页，返回指定实体对象
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryData(String sql, Map<String, Object> parameters, Class<T> tClass) {
        Query query = null;
        query = entityManager.createNativeQuery(sql, tClass);
        if (parameters != null) {
            Set<Entry<String, Object>> set = parameters.entrySet();
            for (Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        List<T> rows = query.getResultList();
        return rows;
    }

    /**
     * @param sql
     * @param parameters
     * @return
     * @throws @Author：liujian 2018年4月10日 上午10:07:40
     * @Title: queryDataToCaseInsensitiveMap
     * @Description: 原生sql查询主库数据，不进行分页，返回的字段名不区分大小写
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryDataToCaseInsensitiveMap(String sql, Map<String, Object> parameters) {
        Query query = null;
        query = entityManager.createNativeQuery(sql);
        if (parameters != null) {
            Set<Entry<String, Object>> set = parameters.entrySet();
            for (Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityCaseInsensitiveMapResultTransformer.INSTANCE);
        List<Map<String, Object>> rows = query.getResultList();
        return rows;
    }

    public int executeUpdate(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String _sSql, List<Object> _params, int _nStartIndex, int _nPageSize) {
        Query query = entityManager.createQuery(_sSql);
        if (_params != null && !_params.isEmpty()) {
            for (int i = 0; i < _params.size(); i++) {
                query.setParameter(i, _params.get(i));
            }
        }
        if (_nStartIndex >= 0 && _nPageSize > 0) {
            query.setFirstResult(_nStartIndex);
            query.setMaxResults(_nPageSize);
        }
        return query.getResultList();
    }

    public int count(String _sSql, List<Object> _params) {
        return ((Long) find(_sSql, _params, -1, -1).listIterator().next()).intValue();
    }

    /**
     * Description:分页查询，sql参数为List<Object> <BR>
     *
     * @param sSql
     * @param countSql
     * @param params
     * @param nStartPage
     * @param nPageSize
     * @return
     * @throws Exception
     * @author liu.zhuan
     * @date 2018年9月5日 下午5:54:29
     * @version 1.0
     */
    public <T> MyPage<T> findPage(String sSql, String countSql, List<Object> params, int nStartPage, int nPageSize)
            throws Exception {
        List<T> pageList = new ArrayList<T>();
        int count = count(countSql, params);
        MyPage<T> myPage = new MyPage<T>(count, nPageSize);
        myPage.setTotalnum(count);
        myPage.setPage(nStartPage);
        myPage.setSize(nPageSize);
        pageList = find(sSql, params, nStartPage * nPageSize, nPageSize);
        myPage.setContent(pageList);
        return myPage;
    }

    /**
     * @param sSql
     * @param countSql
     * @param params
     * @param nStartPage
     * @param nPageSize
     * @return
     * @throws Exception
     * @throws
     * @Title: findDocsPage
     * @Description: 稿件数据单独查询，稿件数据量较大，只适用于单表查询
     * @Author：liujian 2019年2月25日 下午3:15:16
     */
    public <T> MyPage<T> findDocsPage(String sSql, String countSql, Map<String, Object> params, int nStartPage, int nPageSize, Class<T> tClass)
            throws Exception {
        List<T> pageList = new ArrayList<T>();
        String lowSql = sSql.toLowerCase();
        String selectFiled = lowSql.substring(0, lowSql.indexOf(" from "));
        String fromStr = lowSql.substring(0, lowSql.indexOf(" where "));
//        String newCountSql = lowSql.replace(selectFiled,"select count(id) ");
        String joinSql = lowSql.replace(selectFiled, "select id ") + " limit " + nStartPage * nPageSize + "," + nPageSize;
        String fainlSql = fromStr.replace(selectFiled, "select * ") + " join (" + joinSql + ") joinTabe using (id)";
//        System.out.println("newCountSql===" + newCountSql);
        System.out.println("joinSql===" + fainlSql);
        int count = countDocs(countSql, params);
        pageList = findDocs(fainlSql, params, tClass);
        MyPage<T> myPage = new MyPage<T>(count, nPageSize);
        myPage.setTotalnum(count);
        myPage.setPage(nStartPage);
        myPage.setSize(nPageSize);
        myPage.setContent(pageList);
        return myPage;
    }

    /**
     * @param _sSql
     * @param _params
     * @return
     * @throws
     * @Title: countDocs
     * @Description: 单独的稿件数据查询
     * @Author：liujian 2019年2月26日 下午2:42:14
     */
    public int countDocs(String _sSql, Map<String, Object> _params) {
        Query query = entityManager.createNativeQuery(_sSql);
        if (_params != null && !_params.isEmpty()) {
            Set<Entry<String, Object>> set = _params.entrySet();
            for (Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return ((BigInteger) query.getResultList().listIterator().next()).intValue();
    }

    /**
     * @param _sSql
     * @param _params
     * @return
     * @throws
     * @Title: findDocs
     * @Description: 单独的稿件数据查询
     * @Author：liujian 2019年2月26日 下午2:42:18
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findDocs(String _sSql, Map<String, Object> _params, Class<T> tClass) {
        Query query = null;
        if (null == tClass || Map.class.equals(tClass)) {
            query = entityManager.createNativeQuery(_sSql);
        } else {
            query = entityManager.createNativeQuery(_sSql, tClass);
        }
        if (_params != null && !_params.isEmpty()) {
            Set<Entry<String, Object>> set = _params.entrySet();
            for (Entry<String, Object> entry : set) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        if (null == tClass || Map.class.equals(tClass)) {
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        List<T> rows = query.getResultList();
        return rows;
    }

//    public static void main(String[] args) {
//
//        String sql = "select u from user u where u.channelId = 8 and u.xid = 8 order by c.id";
//
//        String lowSql = sql.toLowerCase();
//        String selectFiled = lowSql.substring(0, lowSql.indexOf(" from "));
//        String fromStr = lowSql.substring(0, lowSql.indexOf(" where "));
//        String joinSql = lowSql.replace(selectFiled, "select id ") + " limit 1000,50";
//        String fainlSql = fromStr.replace(selectFiled, "select * ") + " join ( " + joinSql + ") joinTabe using (id)";
//        System.out.println(sql);
//        System.out.println(joinSql);
//        System.out.println(fainlSql);
//
//        // 新建 MySQL Parser
//        SQLStatementParser parser = new MySqlStatementParser(sql);
//
//        // 使用Parser解析生成AST，这里SQLStatement就是AST
//        SQLStatement statement = parser.parseStatement();
//
//        // 使用visitor来访问AST
//        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
//        statement.accept(visitor);
//
//        System.out.println(visitor.getColumns());
//        System.out.println(visitor.getOrderByColumns());
//        System.out.println(visitor.getTables());
//        System.out.println(visitor.getConditions());
//        System.out.println(visitor.getParameters());
//        System.out.println(visitor.getRelationships());
//    }
}
