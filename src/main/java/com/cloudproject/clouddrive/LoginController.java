package com.cloudproject.clouddrive;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	private AmazonClient amazonClient;
	
	@Autowired
	LoginController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }
	
	@Autowired JdbcTemplate jdbcTemplate;
	@GetMapping("/login")
	public String getLogin() {
	
		return "Login";
	}
	
	
	@PostMapping("/login")
	public String login(@ModelAttribute(name="loginform") LoginForm loginform, Model model,  HttpSession session) {
		
		String Username = loginform.getUsername();
		String Password = loginform.getPassword();
		String DB_Username = "";
		String DB_Password = "";
		String First_Name ="";
		String Middle_Name ="";
		String Last_Name ="";
		
		String SELECT_SQL = "SELECT * FROM User where Email = ?";
		
		session.setAttribute("isLoggedIn", true);
		session.setAttribute("email", Username);

		User user = null;
		try {
			 user = jdbcTemplate.queryForObject(SELECT_SQL, new UserRowMapper(), loginform.getUsername());
		} catch(DataAccessException dae) {
			dae.printStackTrace();
		}
		
		if(user == null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "User Not Available");
			return "Login";
		}
		DB_Username =user.getEmail();
		DB_Password =user.getPassword();
		First_Name=user.getFirst_Name();
		Middle_Name=user.getMiddle_Name();
		Last_Name=user.getLast_Name();
		session.setAttribute("firstName", First_Name);
		session.setAttribute("middleName", Middle_Name);
		session.setAttribute("lastName", Last_Name);
		
		if(DB_Username.isEmpty() || DB_Username == null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "User Not Available");
			return "Login";
		}
		else if(DB_Username.equals(Username) && DB_Password.equals(Password)) {
			this.amazonClient.listObjects(model);
			return "list";
		}
		else {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "Invalid Credential");
			return "Login";
		}
			
	}
	
	@GetMapping("/signup")
	public String signup() {
	
		return "signup";
	}
//	@GetMapping("/index")
//	public String index( HttpServletRequest request) {
//		if(BucketController.isLoggedIn(request.getSession())) {
//			//this.amazonClient.listObjects(model);
//			return "list";
//		}
//		else
//			return "index";
//	}
	@Autowired UserDetailsDAO UserDetailsDAO; 
	
	@PostMapping("/signup")
	public String signup( @ModelAttribute("signupform") User user, Model model, RedirectAttributes redirectAttributes) {
		
		String Username = user.getEmail();
		String Password = user.getPassword();
		String FirstName = user.getFirst_Name();
		String MiddleName = user.getMiddle_Name();
		String LastName = user.getLast_Name();
		System.out.println("Inside SignUp Controller ********");
		System.out.println(Username  + "***** " + Password+ "***** " + FirstName+ "****** " +MiddleName + "*** " + LastName);
		
		UserDetailsDAO.addNewUser(user);

		return "signup";

	}
	
	@GetMapping("/logout")
	public String signout(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("isLoggedIn", false);
		request.getSession().setAttribute("email", "");
		request.getSession().setAttribute("firstName", "");
		request.getSession().setAttribute("lastName", "");
		request.getSession().setAttribute("middleName", "");
		
		return "Login";
	}
	
}
