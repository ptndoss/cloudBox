package com.cloudproject.clouddrive;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;

public class FileRowMapper implements RowMapper<FileUpload>{
//<HashMap<Integer,FileUpload>> {
	
	@Override
	public FileUpload mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(rowNum + " test test test test");
		FileUpload fileUpload = new FileUpload();
		HashMap<Integer,FileUpload> mapRet= new HashMap<Integer,FileUpload>();
		while(rs.next()) {
		fileUpload.setEmail(rs.getString("Email"));
		fileUpload.setFileName(rs.getString("FileName"));
		fileUpload.setDescription(rs.getString("Description"));
		fileUpload.setUploadTime(rs.getString("UploadTime"));
		System.out.println("Inside While LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOP!!!");
		mapRet.put(rowNum, fileUpload);
		}
		return fileUpload;
	}	


}
