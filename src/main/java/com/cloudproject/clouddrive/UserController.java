package com.cloudproject.clouddrive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired UserDetailsDAO UserDetailsDAO; 
	
	@PostMapping("/addUser")
	public ResponseEntity<Object> addNewUser(@RequestBody User user) {
		System.out.println("Password ---------" +user.getPassword());
		UserDetailsDAO.addNewUser(user);
		return new ResponseEntity<>("UserAdded",HttpStatus.OK);
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<Object> getUser(String email) {
		
		UserDetailsDAO.getUser(email);
		return new ResponseEntity<>("Listed",HttpStatus.OK);
	}
}
