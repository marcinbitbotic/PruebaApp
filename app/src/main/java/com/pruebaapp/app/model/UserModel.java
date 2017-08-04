package com.pruebaapp.app.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public class UserModel {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("birthdate")
	private Date birthDate;

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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
