package com.cloudproject.clouddrive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class UserDetailsDAOImplementation implements UserDetailsDAO {

	@Autowired JdbcTemplate jdbcTemplate;
	
	
	@Override
	public void addNewUser(User user) {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		String INSRT_SQL = "INSERT INTO User VALUES(?,?,?,?,?) ";
		try{
		
		
		connection = jdbcTemplate.getDataSource().getConnection();
		System.out.println("Inside DB Connection####");
		System.out.println("EMAIL -----" + user.getEmail());
		System.out.println("First Name -----" + user.getFirst_Name());
		System.out.println(user.getLast_Name());
		
		
		PreparedStatement preparedstatement = connection.prepareStatement(INSRT_SQL);
		preparedstatement.setString(1, user.getEmail());
		preparedstatement.setString(2, user.getPassword());
		preparedstatement.setString(3, user.getFirst_Name());
		preparedstatement.setString(4, user.getMiddle_Name());
		preparedstatement.setString(5, user.getLast_Name());
		preparedstatement.executeUpdate();
		preparedstatement.close();
		}catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            if (connection != null) {
                try {
                	connection.close();
                } catch (SQLException e) {}

            }
        }
    }


	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		String SELECT_SQL = "SELECT * FROM User where Email = ?";
			
		User user = jdbcTemplate.queryForObject(SELECT_SQL, new UserRowMapper(), email);
		System.out.println("FIrst Name  -----" + user.getFirst_Name());
		return user;

		}
}
