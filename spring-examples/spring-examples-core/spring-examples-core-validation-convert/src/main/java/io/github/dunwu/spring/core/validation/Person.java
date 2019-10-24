package io.github.dunwu.spring.core.validation;

public class Person {

	private String name;

	@sinceString
	private String birthday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
