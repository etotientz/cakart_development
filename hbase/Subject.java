package com.suphalaam.hbase;

public class Subject {
	private int id;
	private String name;
	
	
	
	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + "]";
	}
	public Subject(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
