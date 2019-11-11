package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.Note;
import cn.houlinan.mylife.entity.RegistrationCode;

import java.io.Serializable;
import java.util.List;

public interface RegistrationCodeRepository extends BaseJpaRepository<RegistrationCode, Serializable> {

    List<RegistrationCode> findRegistrationCodesByIsUserdAndIsCreate(Integer isUserd ,Integer isCreate);

    RegistrationCode findRegistrationCodeById(String id);

}
