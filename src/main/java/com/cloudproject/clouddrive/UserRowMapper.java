package com.cloudproject.clouddrive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(rowNum + " test test test test");
		User user = new User();
		user.setEmail(rs.getString("Email"));
		user.setPassword(rs.getString("Password"));
		user.setFirst_Name(rs.getString("First_Name"));
		user.setLast_Name(rs.getString("Last_Name"));
		user.setMiddle_Name(rs.getString("Middle_Name"));
		return user;
	}	

}
