package com.cloudproject.clouddrive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDetailsDAOImplementation implements FileDetailsDAO {

	
	@Autowired JdbcTemplate jdbcTemplate;
//	
	Date UploadDate = new Date();
	
	
	
	@Override
	public void addNewfile(String email, String filename, String Desc) {
		// TODO Auto-generated method stub

		Connection connection = null;
		
		String INSRT_SQL = "INSERT INTO FileUpload (Email, FileName,Description, UploadTime) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE Description = VALUES(Description), UploadTime = VALUES(UploadTime)";
		try{
		
		
		connection = jdbcTemplate.getDataSource().getConnection();
		System.out.println("Inside DB Connection####");

		
		PreparedStatement preparedstatement = connection.prepareStatement(INSRT_SQL);
		preparedstatement.setString(1, email);
		preparedstatement.setString(2, filename);
		preparedstatement.setString(3, Desc);
		preparedstatement.setString(4, UploadDate.toString());
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
	public List<FileUpload> getFileDetails(String email) {
		
		
		
		List<FileUpload> fileList= new ArrayList<FileUpload>();
		
		Connection connection = null;
		// TODO Auto-generated method stub
		
		try {
		String SELECT_SQL = "SELECT * FROM FileUpload where Email = ?";
//		PreparedStatement preparedstatement = connection.prepareStatement(SELECT_SQL);
//		preparedstatement.setString(1, email);
		List<Map<String, Object>> rows =jdbcTemplate.queryForList(SELECT_SQL,email); 
//				JdbcTemplate.queryForList(SELECT_SQL); 
//		jdbcTemplate.q
				
		
		for(Map fileDetails: rows) {
			FileUpload fileUpload = new FileUpload();
			fileUpload.setEmail((String)fileDetails.get("Email"));
			fileUpload.setFileName((String)fileDetails.get("FileName"));
			fileUpload.setDescription((String)fileDetails.get("Description"));
			fileUpload.setUploadTime((String)fileDetails.get("UploadTime"));
			
			fileList.add(fileUpload);
		}
//				
//		for()
//		
//		
//		
//		
//		
//		
//		try{
//			
//			
//			connection = jdbcTemplate.getDataSource().getConnection();
//			System.out.println("Inside DB Connection####");
//			
//			
//			
//
//		
//		
//		preparedstatement.executeQuery();
//		int i = preparedstatement.getFetchSize();
//		System.out.println("COUNT OF NUMBER OF RECORDS " +email +  i + "^^^^^^^^^^^^^^^^^^^");
//
//			
//		int count;	
//		FileUpload listFromDB = jdbcTemplate.queryForObject(SELECT_SQL, new FileRowMapper(), email);
//		
//		System.out.println("For Email " + email + " " +"^^^^^^^^^^^^^^^^^^^" + listFromDB.getEmail());
////		
//		while(mapFiles.get(key))
//		mapFiles.get(key);
//		
//		
//		System.out.println("FIrst Name  -----" + fileUpload.getEmail());
//		
		}catch (Exception e) {

            throw new RuntimeException(e);

        } finally {
            if (connection != null) {
                try {
                	connection.close();
                } catch (SQLException e) {}

            }
        }
		
		
		return fileList;

	
	}


	@Override
	public void deleteFileDetails( String email, String fileName) {
		// TODO Auto-generated method stub
		
Connection connection = null;
		
		String INSRT_SQL = "DELETE FROM FileUpload WHERE Email = ? AND FileName = ?";
				
		try{
		
		
		connection = jdbcTemplate.getDataSource().getConnection();
		System.out.println("Inside DB Connection####");

		
		PreparedStatement preparedstatement = connection.prepareStatement(INSRT_SQL);
		preparedstatement.setString(1, email);
		preparedstatement.setString(2, fileName);

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



		
	}

