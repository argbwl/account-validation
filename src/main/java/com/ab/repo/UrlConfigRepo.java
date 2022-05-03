package com.ab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ab.entity.UrlConfigEntity;

@Repository
public interface UrlConfigRepo extends JpaRepository<UrlConfigEntity, Integer>{
	
	public UrlConfigEntity findByUri(String uri);

}
