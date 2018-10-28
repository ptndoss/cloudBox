package com.cloudproject.clouddrive;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @Value("${amazonProperties.cloudfrontUrl}")
    private String cloudfrontUrl;

@SuppressWarnings("deprecation")
@PostConstruct
    private void initializeAmazon() {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       this.s3client = new AmazonS3Client(credentials);
}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
	
	private void uploadFileTos3bucket(String fileName, File file) {
	    s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
	    
	    	
	    
	}
	
	@Autowired JdbcTemplate jdbcTemplate;
	
	@Autowired FileDetailsDAO FileDetailsDAO;
	public String uploadFile(MultipartFile multipartFile,String fileName, String description, String email, Model model) {
		
		try {
			String userName = email;
			String FileName = fileName;
			String desc = description;
			Date lastUpdateTime = new Date();

			System.out.println("Inside SignUp Controller ********");
			System.out.println(userName  + "***** " + FileName+ "***** " + desc+ "****** " +lastUpdateTime + "*** ");
		
			FileDetailsDAO.addNewfile(userName, FileName, desc);
			
			
			File file = convertMultiPartToFile(multipartFile);
	        uploadFileTos3bucket(file.toString(), file);
	        file.delete();
	        model.addAttribute("Message", "Success");
	        listObjects(model);      
	        
	    } catch (Exception e) {
	       e.printStackTrace();
	       model.addAttribute("Message", "Error");
	    }
	    return "/list";
	}
	
	public String deleteFileFromS3Bucket(String fileName,Model model, HttpServletRequest request) {

		String email= (String) request.getSession().getAttribute("email");
	    s3client.deleteObject(new DeleteObjectRequest(bucketName , fileName));
	    
	    FileDetailsDAO.deleteFileDetails(email,fileName);
	    listObjects( model);
	    return "/list";
	}
	
//	public String downloadFromS3Bucket(String fileUrl) {
//	    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//	    System.out.println(fileName);
//	    s3client.deleteObject(new DeleteObjectRequest(bucketName , fileName));
//	    return fileName;
//	}
	@Autowired 
	 private HttpSession httpSession;

	@Autowired UserDetailsDAO UserDetailsDAO; 
	
	@ModelAttribute("objectlist")
	public String listObjects(Model model) {
//		String list = "";
		String downloadURL=""; 
		String deleteURL=endpointUrl + "/";
//		List<String> fileNames = new ArrayList<String>();
//		List<String> UserName = new ArrayList<String>();
//		List<Date> LastModified = new ArrayList<Date>();
//		List<String> Description = new ArrayList<String>();
		ObjectListing objectListing = s3client.listObjects(bucketName);
		List<S3ObjectSummary> Objects= objectListing.getObjectSummaries();
		downloadURL = cloudfrontUrl + "/";
		FileUpload files = new FileUpload();
		String email = (String) httpSession.getAttribute("email");
		String First_Name = (String) httpSession.getAttribute("firstName");
		String Middle_Name = (String) httpSession.getAttribute("middleName");
		String Last_Name = (String) httpSession.getAttribute("lastName");
		
		List<FileUpload> filelist= FileDetailsDAO.getFileDetails(email);
		
//		String description = "";//.get(arg0)
//		String UploadTime = files.getUploadTime();
//		String fileName = files.getFileName();
		
//		String SELECT_SQL = "SELECT * FROM User where Email = ?";
		
		System.out.println("++++++++++++Email from List ++++" +email + "test test test ");
		
//		User user = null;
//		try {
//			 user = jdbcTemplate.queryForObject(SELECT_SQL, new UserRowMapper(), email);
//			 First_Name = user.getFirst_Name();
//			 Middle_Name = user.getMiddle_Name();
//			 Last_Name = user.getLast_Name();
//		} catch(DataAccessException dae) {
//			dae.printStackTrace();
//		}
		model.addAttribute("Filelist", filelist);
		model.addAttribute("Objectlist", Objects);
		model.addAttribute("First_Name", First_Name);
		model.addAttribute("Middle_Name", Middle_Name);
		model.addAttribute("Last_Name", Last_Name);
		model.addAttribute("Download", downloadURL);

	   return "list";
	}

}