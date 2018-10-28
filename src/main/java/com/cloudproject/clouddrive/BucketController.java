package com.cloudproject.clouddrive;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;



//@Request																						Mapping("/storage")
@Controller
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }
    

	@Autowired JdbcTemplate jdbcTemplate;
    
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file", required=true) MultipartFile file, @RequestPart(value = "description", required=false) String description,Model model, HttpServletRequest request) {
    	String email= (String) request.getSession().getAttribute("email");
    	String fileName =  file.getOriginalFilename();
    	System.out.println("Email in Upload file " + email + "File NAme "  + fileName + "Test Test Test Test..");
    	if(!isLoggedIn(request.getSession()))
    	{
    		return "Login";
    	}
    	else
    		request.setAttribute("description", description);
    		this.amazonClient.uploadFile(file, fileName, description, email, model);
    	
    		
    	return "list";
    }
    
    
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
    
    @GetMapping("/list")
    public String list( Model model, HttpServletRequest request) {
    	if(!isLoggedIn(request.getSession()))
    		return "Login";
    	this.amazonClient.listObjects( model);
    	return "list";
    }


    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam(value="fileName", required=false) String fileName,Model model, HttpServletRequest request) {

    	return this.amazonClient.deleteFileFromS3Bucket(fileName, model,request);
    }
    
    public static Boolean isLoggedIn(HttpSession session) {
    	Boolean bRet = false;
    	if(session != null) {
    		bRet = (Boolean)session.getAttribute("isLoggedIn");
    	}
    	if(bRet == null)
    		bRet = false;
    	return bRet;
    }
}