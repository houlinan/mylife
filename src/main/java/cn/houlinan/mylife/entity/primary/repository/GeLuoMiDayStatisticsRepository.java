package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.GeLuoMiDayStatistics;
import cn.houlinan.mylife.entity.GeLuoMiUser;
import cn.houlinan.mylife.entity.Team;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface GeLuoMiDayStatisticsRepository extends BaseJpaRepository<GeLuoMiDayStatistics, Serializable> {

    GeLuoMiDayStatistics findGeLuoMiDayStatisticsByStatisticsTimeAndGeluomiUserName
            (String statisticsTime , String geluomiUserName);

    List<GeLuoMiDayStatistics> findGeLuoMiDayStatisticsByStatisticsTimeAndTeam
            (String statisticsTime , Team team);


    List<GeLuoMiDayStatistics> findAllByStatisticsDateBetweenAndTeam(Date start , Date end , Team team);

    List<GeLuoMiDayStatistics> findAllByStatisticsDateBetweenAndGeluomiUserNameOrderByStatisticsDateAsc(
            Date start , Date end ,String geluomiUserName  );

    List<GeLuoMiDayStatistics> findAllByTeam(Team team);
}
