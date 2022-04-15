package com.ab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ab.entity.AccountAuthEntity;


@Repository
public interface AccountAuthRepo extends JpaRepository<AccountAuthEntity, Integer>{

	public AccountAuthEntity findByAccNameAndAccNumber(String accName, String accNumber);
	
	@Modifying
	@Query("update AccountAuthEntity e set e.status = :status where e.id = :id")
	public int updateStatusById(@Param("status") String Status, @Param("id") int id);
	
	public AccountAuthEntity findByAccNumber(String accNumber);
	
	
}
