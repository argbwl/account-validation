package com.ab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "static_table")
public class StaticDataEntity extends DefaultColumnEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String keyName;
	private String keyParam;
	private String keyValue;
	private boolean keyInd;
	private int keyVersion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyParam() {
		return keyParam;
	}

	public void setKeyParam(String keyParam) {
		this.keyParam = keyParam;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public boolean isKeyInd() {
		return keyInd;
	}

	public void setKeyInd(boolean keyInd) {
		this.keyInd = keyInd;
	}

	public int getKeyVersion() {
		return keyVersion;
	}

	public void setKeyVersion(int keyVersion) {
		this.keyVersion = keyVersion;
	}

}
