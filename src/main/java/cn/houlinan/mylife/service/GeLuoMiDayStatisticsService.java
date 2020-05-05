package cn.houlinan.mylife.service;

import cn.houlinan.mylife.entity.GeLuoMiDayStatistics;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.GeLuoMiDayStatisticsRepository;
import cn.houlinan.mylife.utils.BeanCopy;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/2
 * Time : 8:34
 */
@Service
@Slf4j
public class GeLuoMiDayStatisticsService {

    @Autowired
    Sid sid;

    @Autowired
    GeLuoMiDayStatisticsRepository geLuoMiDayStatisticsRepository;

    @Value("${upload.tempPath}")
    private String excelCreatePath;

    public void save(GeLuoMiDayStatistics geLuoMiDayStatistics) throws Exception {

        if (CMyString.isEmpty(geLuoMiDayStatistics.getId()))
            geLuoMiDayStatistics.setId(sid.nextShort());

        if (geLuoMiDayStatistics.getStatisticsDate() == null) {
            Date date = cn.houlinan.mylife.utils.DateUtil.parseDate(geLuoMiDayStatistics.getStatisticsTime() + " 00:01:01", "yyyy-MM-dd HH:mm:ss");
            geLuoMiDayStatistics.setStatisticsDate(date);
        }

        geLuoMiDayStatisticsRepository.save(geLuoMiDayStatistics);
    }

    public void save(GeLuoMiDayStatistics geLuoMiDayStatistics, User user) throws Exception {

        if (CMyString.isEmpty(geLuoMiDayStatistics.getGeluomiUserName())) {
            geLuoMiDayStatistics.setGeluomiUserName(user.getUserName());
        }
        if (CMyString.isEmpty(geLuoMiDayStatistics.getGeluomiUserNikeName())) {
            geLuoMiDayStatistics.setGeluomiUserNikeName(user.getNikeName());
        }

        GeLuoMiDayStatistics findResult = geLuoMiDayStatisticsRepository.findGeLuoMiDayStatisticsByStatisticsTimeAndGeluomiUserName
                (geLuoMiDayStatistics.getStatisticsTime(), geLuoMiDayStatistics.getGeluomiUserName());


        if (findResult != null) {
            String id = findResult.getId();
            BeanUtils.copyProperties(geLuoMiDayStatistics, findResult);
            findResult.setId(id);
            geLuoMiDayStatistics = findResult;
        }

        String teamid = geLuoMiDayStatistics.getTeamid();
        if (CMyString.isEmpty(teamid) || geLuoMiDayStatistics.getTeam() == null) {
            geLuoMiDayStatistics.setTeamid(user.getTeamid());
            geLuoMiDayStatistics.setTeam(user.getTeam());
        }
        save(geLuoMiDayStatistics);
    }

    public List<GeLuoMiDayStatistics> queryDateByDataTime(String dateTimeStr, User user) throws Exception {

        Team team = user.getTeam();
        if (team == null) throw new Exception("没有找到您的小组信息，请绑定后重试");

        if (CMyString.isEmpty(dateTimeStr)) dateTimeStr = DateUtil.format(new Date(), "yyyy-MM-dd");

        return geLuoMiDayStatisticsRepository.findGeLuoMiDayStatisticsByStatisticsTimeAndTeam(dateTimeStr, team);
    }

    public String forMatDataByList(List<GeLuoMiDayStatistics> dataTotalList, String userNames) {
        //将数据转换成Map<用户名称 , list<GeLuoMiDayStatistics>> 这样的格式
//        Map<String , List<GeLuoMiDayStatistics>> forMatListUserName_ListStatistics = new HashMap<>( );

        MultiValueMap<String, GeLuoMiDayStatistics> forMatListUserName_ListStatistics = new LinkedMultiValueMap<>();

        List<String> userNamesList = Arrays.asList(userNames.split(","));

        //装用户名称 key = username  。value = nikeName
        Map<String, String> userNameMappingMap = new HashMap<>();

        for (int i = 0; i < dataTotalList.size(); i++) {
            GeLuoMiDayStatistics geLuoMiDayStatistics = dataTotalList.get(i);
            if (userNamesList.size() != 0 && !userNamesList.contains(geLuoMiDayStatistics.getGeluomiUserName())) {
                continue;
            }

            forMatListUserName_ListStatistics.add(geLuoMiDayStatistics.getGeluomiUserName(), geLuoMiDayStatistics);
            userNameMappingMap.put(geLuoMiDayStatistics.getGeluomiUserName(), geLuoMiDayStatistics.getGeluomiUserNikeName());
        }

        log.info("获取到的forMatListUserName_ListStatistics.size" + forMatListUserName_ListStatistics.size());

        TreeSet<String> allDateStrList = new TreeSet<>();


        //创建一下下一层结果集。
        Map<String, Map<String, Map<String, Double>>> ForMatResult = new HashMap<>();

        //处理每个用户里面的数据
        forMatListUserName_ListStatistics.forEach((k, v) -> {
            //将数据转成Map<日期 ，Map<String , double>>  二层map为key和value四个体重数据
            Map<String, Map<String, Double>> forMatUserDataToMap = new HashMap<>();
            for (int i = 0; i < v.size(); i++) {

                Map<String, Double> currDataMap = new HashMap<>();

                GeLuoMiDayStatistics geLuoMiDayStatistics = v.get(i);
                String statisticsTime = geLuoMiDayStatistics.getStatisticsTime();
                //处理数据
                currDataMap.put("morningWeight", geLuoMiDayStatistics.getMorningWeight());
                currDataMap.put("nightWeight", geLuoMiDayStatistics.getNightWeight());
                currDataMap.put("morningWaistline", geLuoMiDayStatistics.getMorningWaistline());
                currDataMap.put("nightWaistline", geLuoMiDayStatistics.getNightWeight());
                forMatUserDataToMap.put(statisticsTime, currDataMap);
                allDateStrList.add(statisticsTime);

            }
            ForMatResult.put(k, forMatUserDataToMap);
        });

//        allDateStrList.forEach(a -> log.error(a));

        String excelTolocal = createExcelTolocal(ForMatResult, allDateStrList, userNameMappingMap);
        String localFilePath = excelCreatePath.trim() + "/geLuoMiTempPath/" + excelTolocal + ".xlsx";

        log.info("文件路径 : {} ", localFilePath);
        return localFilePath;
    }


    public String createExcelTolocal(Map<String, Map<String, Map<String, Double>>> ForMatResult,
                                     TreeSet<String> allDateStrList, Map<String, String> userNameMappingMap) {
        //通过工具类创建writer
        String uuid = UUID.randomUUID().toString();
        //输出路径
        ExcelWriter writer = ExcelUtil.getWriter(excelCreatePath + "/geLuoMiTempPath/" + uuid + ".xlsx");


        //设置头部单元格样式
        CellStyle headCellStyle = writer.getHeadCellStyle();
        Font font = writer.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setItalic(true);
        headCellStyle.setFont(font);

        writer.setColumnWidth(-1, 15);
        writer.setColumnWidth(0, 45);
        writer.autoSizeColumn(0);
        writer.setRowHeight(1, 20);


        writer.merge(1 + allDateStrList.size() + 2, "导出数据结果");

        List writeList = new ArrayList<>();

        ForMatResult.forEach((k, v) -> {

            Map<String, Object> row1 = addDataToRowData(k, v, allDateStrList, userNameMappingMap);
            writeList.add(row1);
        });

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(writeList, true);
        // 关闭writer，释放内存
        writer.close();
        return uuid;
    }

    public Map<String, Object> addDataToRowData(String userName, Map<String, Map<String, Double>> rowData,
                                                TreeSet<String> allDateStrList,
                                                Map<String, String> userNameMappingMap) {

        Map<String, Object> row1 = new LinkedHashMap<>();

        row1.put("姓名", userNameMappingMap.get(userName));

        int size = allDateStrList.size();

        //处理日掉体重
        String riDiaoTiZhong = "";
        String riDiaoYaoWei = "";

        //处理总掉秤
        String totalBeforeRiDiaoCheng = "";
        String totalAfterRiDiaoYaoWei = "";

        //处理掉秤比例
        String diaoChengBili = "";
        String diaoYaoWeiBiLi = "";

        //处理日掉秤数据
        if (size > 1) {
            //查询最后一个日期和倒数第二个日期
            List<String> treeSets = new ArrayList<>(allDateStrList);
            String lastDateStr = treeSets.get(treeSets.size() - 1);
            String beforeLastDateStr = treeSets.get(treeSets.size() - 2);

            String totalBeforeSet = treeSets.get(0);


            //最后一个数据
            Map<String, Double> lastDateStrMap = rowData.get(lastDateStr);
            //最后前一个数据
            Map<String, Double> beforeLastDateStrMap = rowData.get(beforeLastDateStr);
            //第一天的数据拿出来 进行总掉秤处理
            Map<String, Double> totalBeforeDateStrMap = rowData.get(totalBeforeSet);


            //日掉数值
            if (lastDateStrMap.get("morningWeight") != null && beforeLastDateStrMap.get("morningWeight") != null) {

                Double morningWeight = lastDateStrMap.get("morningWeight");
                Double morningWeight1 = beforeLastDateStrMap.get("morningWeight");
                riDiaoTiZhong = String.valueOf(morningWeight - morningWeight1);

            }
            if (lastDateStrMap.get("morningWaistline") != null && beforeLastDateStrMap.get("morningWaistline") != null) {

                Double morningWaistline = lastDateStrMap.get("morningWaistline");
                Double morningWaistline1 = beforeLastDateStrMap.get("morningWaistline");
                riDiaoYaoWei = String.valueOf(morningWaistline1 - morningWaistline);
            }

            //总掉称比例
            if (lastDateStrMap.get("morningWeight") != null && totalBeforeDateStrMap.get("morningWeight") != null) {

                Double morningWeight = lastDateStrMap.get("morningWeight");
                Double morningWeight1 = totalBeforeDateStrMap.get("morningWeight");
                totalBeforeRiDiaoCheng = String.valueOf(morningWeight - morningWeight1);
            }

            if (lastDateStrMap.get("morningWaistline") != null && totalBeforeDateStrMap.get("morningWaistline") != null) {

                Double morningWaistline = lastDateStrMap.get("morningWaistline");
                Double morningWaistline1 = totalBeforeDateStrMap.get("morningWaistline");
                totalAfterRiDiaoYaoWei = String.valueOf(morningWaistline - morningWaistline1);
            }

            //总掉称比例
            if (lastDateStrMap.get("morningWeight") != null && totalBeforeDateStrMap.get("morningWeight") != null) {

                Double morningWeight = lastDateStrMap.get("morningWeight");
                Double morningWeight1 = totalBeforeDateStrMap.get("morningWeight");
                diaoChengBili = String.valueOf(morningWeight - morningWeight1);
            }

            if (lastDateStrMap.get("morningWaistline") != null && totalBeforeDateStrMap.get("morningWaistline") != null) {

                Double morningWaistline = lastDateStrMap.get("morningWaistline");
                Double morningWaistline1 = totalBeforeDateStrMap.get("morningWaistline");
                diaoYaoWeiBiLi = String.valueOf(morningWaistline - morningWaistline1);
            }
        }


        allDateStrList.forEach(a -> {
            Map<String, Double> stringDoubleMap = rowData.get(a);
            if (stringDoubleMap == null) {
                row1.put(a + " \n 体重", "");
                row1.put(a + " \n 腰围", "");
            } else {
                row1.put(a + " \n 体重", stringDoubleMap.get("morningWeight"));
                row1.put(a + " \n 腰围", stringDoubleMap.get("morningWaistline"));
            }
        });

        //添加日掉体重和日掉腰围
        row1.put("日掉体重", riDiaoTiZhong);
        row1.put("日掉腰围", riDiaoYaoWei);
        row1.put("总掉称值", totalBeforeRiDiaoCheng);
        row1.put("总掉腰围值", totalAfterRiDiaoYaoWei);


        return row1;
    }
}
