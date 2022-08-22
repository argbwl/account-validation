package com.ab.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ab.entity.AccountInfoEntity;

@Repository
public interface AccountInfoRepo extends JpaRepository<AccountInfoEntity, Integer> {
	
	public AccountInfoEntity findByAccountNumberAndAccountStatus(String accountNumber, String status);
	public AccountInfoEntity findByAccountNumber(String accountNumber);
	
	@Query("select a from AccountInfoEntity a where a.createDt >= :createdDt order by a.createDt desc")
	public List<AccountInfoEntity> findByGreateThanDate(@Param("createdDt") Date createdDt);
	
	@Query("select a from AccountInfoEntity a where a.createDt < :createdDt and a.accountStatus =:accountStatus")
	public List<AccountInfoEntity> findByGreateThanDateAndStatus(@Param("createdDt") Date daysInterval, @Param("accountStatus") String accountStatus);

	@Modifying
	@Query("update AccountInfoEntity e set e.closingStatus = :status where e.accountNumber = :accountNumber")
	public int updateClosingStatusById(@Param("status") String status, @Param("accountNumber") String accountNumber);

	public AccountInfoEntity findByContactNo(String contactNo);
}
