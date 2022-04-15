package com.ab.test;

public class POJO {

	private int id;
	private String name;
	private String value;
	private String desc;

	public POJO() {
		super();
	}

	public POJO(int id, String name, String value, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "POJO [id=" + id + ", name=" + name + ", value=" + value + ", desc=" + desc + "]";
	}

}
