package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.Team;

import java.io.Serializable;

public interface TeamRepository extends BaseJpaRepository<Team, Serializable> {

    Team findTeamByTeamName(String teamName);

}
