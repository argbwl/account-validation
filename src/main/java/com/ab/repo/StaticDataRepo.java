package com.ab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ab.entity.StaticDataEntity;

@Repository
public interface StaticDataRepo extends JpaRepository<StaticDataEntity, Integer>{
	
	public StaticDataEntity findByKeyNameAndKeyParamAndKeyInd(String keyName, String keyParam, boolean keyInd);
	public List<StaticDataEntity> findByKeyNameAndKeyParam(String keyName, String keyParam);

}
