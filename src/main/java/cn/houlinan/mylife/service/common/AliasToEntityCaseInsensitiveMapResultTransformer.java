package cn.houlinan.mylife.service.common;


import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.Map;

/**
 * @author liujian
 * @ClassName: AliasToEntityCaseInsensitiveMapResultTransformer
 * @Description: 查询结果转换成CaseInsensitiveKeyMap, 不区分大小写
 * @date 2018年4月19日 下午5:23:05
 */
public class AliasToEntityCaseInsensitiveMapResultTransformer extends AliasedTupleSubsetResultTransformer {

    /**
     * serialVersionUID : TODO
     */
    private static final long serialVersionUID = 319969698994884418L;
    public static final AliasToEntityCaseInsensitiveMapResultTransformer INSTANCE = new AliasToEntityCaseInsensitiveMapResultTransformer();

    private AliasToEntityCaseInsensitiveMapResultTransformer() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Object> result = new CaseInsensitiveMap();
        for (int i = 0; i < tuple.length; i++) {
            String alias = aliases[i];
//			System.out.println(alias+"==="+tuple[i]);
            if (alias != null) {
                result.put(alias.toUpperCase(), tuple[i]);
            }
        }
        return result;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }
}
