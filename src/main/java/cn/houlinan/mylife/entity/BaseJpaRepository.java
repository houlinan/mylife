package cn.houlinan.mylife.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseJpaRepository<T,pk extends Serializable> extends JpaRepository<T, pk>, JpaSpecificationExecutor<T> {

}
