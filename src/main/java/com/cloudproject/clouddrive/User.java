package com.cloudproject.clouddrive;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
		
	@Id
	private String Email;
	private String Password;
	private String First_Name;
	private String Middle_Name;
	private String Last_Name;

	
	public String getEmail() {
		return this.Email;
	}
	
	public String getFirst_Name() {
		return this.First_Name;
	}
	
	public String getMiddle_Name() {
		return this.Middle_Name;
	}
	
	public String getLast_Name() {
		return this.Last_Name;
	}

	public void setEmail(String email) {
		Email = email;
	}
	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}
	public void setMiddle_Name(String middle_Name) {
		Middle_Name = middle_Name;
	}
	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}
	

	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String password) {
		Password = password;
	}


}
