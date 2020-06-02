package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.constant.GeLuoMiConstant;
import cn.houlinan.mylife.entity.GeLuoMiDayStatistics;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.GeLuoMiDayStatisticsRepository;
import cn.houlinan.mylife.entity.primary.repository.TeamRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.GeLuoMiDayStatisticsService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.JFreeChartUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.extra.mail.MailUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/1
 * Time : 21:07
 */
@RestController
@RequestMapping("/geluomi/statistics")
@Slf4j
@Api(value = "格洛米统计相关controller", tags = "格洛米统计相关controller")
public class GeLuoMiDayStatisticsController {

    @Autowired
    GeLuoMiDayStatisticsService geLuoMiDayStatisticsService;

    @Autowired
    GeLuoMiDayStatisticsRepository geLuoMiDayStatisticsRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/day/save")
    @ApiOperation(value = "保存今日数据", notes = "保存今日数据")
    public HHJSONResult saveStatistics(GeLuoMiDayStatistics geLuoMiDayStatistics, @ApiIgnore User user) throws Exception {

        geLuoMiDayStatisticsService.save(geLuoMiDayStatistics, user);

        return HHJSONResult.ok();
    }


    @GetMapping("/queryDataByUserNameAndStaTime")
    @ApiOperation(value = "通过日期和用户名称查询数据(精确查询某数据)", notes = "通过日期和用户名称查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = false, dataType = "String", paramType = "query", defaultValue = "test"),
            @ApiImplicitParam(name = "statisticsTime", value = "查询时间", required = false, dataType = "String", paramType = "query", defaultValue = "2020-02-02")
    })
    public HHJSONResult queryDataByUserNameAndStaTime(
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "statisticsTime", required = false) String statistTime, User user) throws Exception {

        if (CMyString.isEmpty(userName)) userName = user.getUserName();
        if (CMyString.isEmpty(statistTime)) statistTime = DateUtil.format(new Date(), "yyyy-MM-dd");
        GeLuoMiDayStatistics findResult = geLuoMiDayStatisticsRepository.findGeLuoMiDayStatisticsByStatisticsTimeAndGeluomiUserName(
                statistTime, userName
        );
        if (findResult == null) throw new Exception("没有找到该用户【" + statistTime + "】的数据");

            return HHJSONResult.ok(findResult);
    }

    @GetMapping("/quertListByDateStr")
    @ApiOperation(value = "查找小组内的某日数据", notes = "查找小组内的某日数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateStr", value = "日期", dataType = "String", paramType = "query", defaultValue = "2020-02-02")
    })
    public HHJSONResult quertListByDateStr(@RequestParam(name = "dateStr", required = false) String dateStr, User user) throws Exception {

        return HHJSONResult.ok(geLuoMiDayStatisticsService.queryDateByDataTime(dateStr, user));
    }

    @GetMapping("/queryTeamDataExportExcel")
    @ApiOperation(value = "导出小组某日到某日的数据为excel。发送至某邮箱", notes = "导出小组数据发送到邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "查询开始日期", dataType = "String", paramType = "query", defaultValue = "2020-02-02"),
            @ApiImplicitParam(name = "endTime", value = "查询结束日期", dataType = "String", paramType = "query", defaultValue = "2020-02-02"),
            @ApiImplicitParam(name = "emailAddress", value = "邮箱地址", dataType = "String", paramType = "query", defaultValue = "houlinan@vip.qq.com"),
            @ApiImplicitParam(name = "userNames", value = "过滤的用户名称", dataType = "String", paramType = "query", defaultValue = "houlinan@vip.qq.com")
    })
    public HHJSONResult queryTeamDataExportExcel(
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime,
            @RequestParam(name = "emailAddress", required = false) String emailAddress,
            @RequestParam(name = "userNames", required = false) String userNames,User user
    ) throws Exception {

        if (CMyString.isEmpty(endTime)) endTime = DateUtil.formatDateTime(new Date());

        if (CMyString.isEmpty(startTime)) {
            Date date = DateUtil.parse(endTime);
            startTime = DateUtil.format(DateUtil.offset(date, DateField.MONTH, -1), "yyyy-MM-dd HH:mm:ss");
        }

        Team team = user.getTeam();
        if(team == null ) return HHJSONResult.errorMsg("您还没有绑定小组信息，请稍后重试");

        List<GeLuoMiDayStatistics> test = geLuoMiDayStatisticsRepository.findAllByStatisticsDateBetweenAndTeam(
                cn.houlinan.mylife.utils.DateUtil.parseDate(startTime),
                cn.houlinan.mylife.utils.DateUtil.parseDate(endTime),
                team);

//        List<GeLuoMiDayStatistics> test = geLuoMiDayStatisticsRepository.findAllByTeam( teamRepository.findTeamByTeamName("test"));

        log.info("查询到的数据有{}条", test.size());

        String filePath = geLuoMiDayStatisticsService.forMatDataByList(test, userNames);

        File excelFile = new File(filePath);
        if (!excelFile.exists()) return HHJSONResult.errorMsg("生成文件失败，请联系管理员");

        if (CMyString.isEmpty(emailAddress)) return HHJSONResult.errorMsg("传入的邮箱为空");

        String titel = "您查询的小组【" + team.getTeamName() + "】查询时间为：" + startTime + " 至 " + endTime + " 的数据! 详见附件和正文";

//        MailUtil.send("houlinan@vip.qq.com", titel, "查询结果详见附件哦！",
//                false, excelFile);

        MailUtil.send(emailAddress, titel, "查询结果详见附件哦！",
                false, excelFile);

        return HHJSONResult.ok();
    }


    @GetMapping("/createDataToDB")
    public HHJSONResult createDataToDB() {

        Team test = teamRepository.findTeamByTeamName("test");
        if (test == null) {
            Team team = new Team();
            team.setId(1L);
            team.setTeamName("test");
            teamRepository.save(team);
        }

        int number = 0;

        for (int a = 0; a < 50; a++) {
            String startDate = "2020-02-02";
            String userName = UUID.randomUUID().toString();

            for (int b = 0; b < 30; b++) {

                startDate = getSpecifiedDayAfter(startDate);

                GeLuoMiDayStatistics currData = new GeLuoMiDayStatistics();

                currData.setGeluomiUserNikeName(userName);
                currData.setGeluomiUserName(userName);
                currData.setStatisticsDate(DateUtil.parse(startDate + " 00:00:01", "yyyy-MM-dd HH:mm:ss"));
                currData.setId(UUID.randomUUID().toString());
                currData.setTeam(test);
                currData.setTeamid(test.getId());
                currData.setStatisticsTime(startDate);

                Integer[] integers = NumberUtil.generateBySet(90, 150, 4);

                currData.setMorningWaistline(Double.valueOf(integers[0]));
                currData.setMorningWeight(Double.valueOf(integers[1]));
                currData.setNightWaistline(Double.valueOf(integers[2]));
                currData.setNightWeight(Double.valueOf(integers[3]));
                geLuoMiDayStatisticsRepository.save(currData);
                number++;
                log.info("准备跑第{}条数据，数据为：{}", number, currData.toString());
            }
        }


        return HHJSONResult.ok();
    }


    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }


    @GetMapping("/queryLineChartByUserName")
    @ApiOperation(value = "查询某人时间内的体重或者腰围曲线情况", notes = "查询某人某段时间内的曲线情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "查询开始日期", dataType = "String", paramType = "query", defaultValue = "2020-02-02"),
            @ApiImplicitParam(name = "endTime", value = "查询结束日期", dataType = "String", paramType = "query", defaultValue = "2020-03-02"),
            @ApiImplicitParam(name = "userName", value = "过滤的用户名称", dataType = "String", paramType = "query", defaultValue = "13204a5e-e158-491e-9342-b3ad1af5184b"),
            @ApiImplicitParam(name = "createType", value = "生成类型，1体重 2腰围 0全部", dataType = "String", paramType = "query", defaultValue = "0")
    })
    public HHJSONResult queryLineChartByUserName(
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime,
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "createType", defaultValue = "0") String createType
    ) throws Exception {

//        User userByUserName = userRepository.findUserByUserName(userName);
//        if(userByUserName == null )  throw new Exception("没有找到该用户的信息");

        if (CMyString.isEmpty(endTime)) endTime = DateUtil.formatDateTime(new Date());

        if (CMyString.isEmpty(startTime)) {
            Date date = DateUtil.parse(endTime);
            startTime = DateUtil.format(DateUtil.offset(date, DateField.MONTH, -1), "yyyy-MM-dd HH:mm:ss");
        }
        List<GeLuoMiDayStatistics> findResult = geLuoMiDayStatisticsRepository.findAllByStatisticsDateBetweenAndGeluomiUserNameOrderByStatisticsDateAsc(
                cn.houlinan.mylife.utils.DateUtil.parseDate(startTime),
                cn.houlinan.mylife.utils.DateUtil.parseDate(endTime),
                userName);

        List<String> strings = Arrays.asList(createType.split(","));

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        // ds.setValue(10, "ibm", "2018-05-21");

        findResult.forEach(a -> {
            if (strings.contains("1") || strings.contains("0"))
                ds.setValue(a.getMorningWeight(), "Weight", a.getStatisticsTime());

            if (strings.contains("2") || strings.contains("0"))
                ds.setValue(a.getMorningWaistline(), "Waistline", a.getStatisticsTime());
        });

        File file = new File(GeLuoMiConstant.GELUOMI_PICS_PATH);
        if (!file.exists()) file.mkdir();

        String uuid =UUID.randomUUID().toString() + ".jpg" ;
        String filePath = GeLuoMiConstant.GELUOMI_PICS_PATH + uuid;
        JFreeChartUtil.createLineChart("用户【"+userName+"】体重腰围数据折线图     (注：Weight(体重)、Waistline(腰围))", "日期", "数值", ds, filePath);

        return HHJSONResult.ok(GeLuoMiConstant.HTTPS_GELUOMI_IMG_ADDRESS + uuid );
    }


}
