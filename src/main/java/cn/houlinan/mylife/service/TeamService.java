package cn.houlinan.mylife.service;

import cn.houlinan.mylife.constant.TeamConstant;
import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.TeamRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * DESC：用户service层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 20:48
 */
@Service
@Slf4j
public class TeamService {

    @Autowired
    TeamRepository teamRepository ;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Sid sid;


    /**
     *DESC:创建小组
     *@param:  teamId
     *@Param: teamName
     *@Param: teamPassword
     *@Param: teamEmail
     *@Param: teamAdminId
     *@Param: isSendAllUser
     *@return:  cn.houlinan.mylife.entity.Team
     *@author hou.linan
     *@date:
    */
    @Transactional
    public Team createTeam(String teamId , String teamName , String teamPassword
            , String teamEmail , User user , Integer isSendAllUser){

        Team team = Team.builder()
                .teamName(teamName)
                .teamPassword(teamPassword)
                .teamEmail(teamEmail)
                .adminUserId(user.getId())
                .isSendAllUser(isSendAllUser).build();

        team.setId(teamId);


        return createTeam(team , user);
    }

    public Team createTeam(Team team , User user){

        if(CMyString.isEmpty(team.getId()) || "0".equals(team.getId())) {
            String uid = sid.nextShort() ;
            team.setId(uid);
            team.setPicsPath(TeamConstant.TEAM_PICS_ROOT_PATH + team.getId());
            team.setCrTime(new Date());
            team.setUpdateTime(new Date());
            team.setAdminUserId(user.getId());
            team.setTeamid(uid);
        }
        teamRepository.save(team);

        user.setTeamid(team.getId());
        user.setTeam(team);
        userRepository.save(user);

        return team ;
    }



}
