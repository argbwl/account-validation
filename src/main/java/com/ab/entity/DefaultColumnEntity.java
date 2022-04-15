package com.ab.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DefaultColumnEntity {

	private Date createDt = new Date();
	private Date modifiedDt = new Date();
	private String modifiedBy;

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getModifiedDt() {
		return modifiedDt;
	}

	public void setModifiedDt(Date modifiedDt) {
		this.modifiedDt = modifiedDt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
