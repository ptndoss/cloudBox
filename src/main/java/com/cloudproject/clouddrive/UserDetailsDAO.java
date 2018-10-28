package com.cloudproject.clouddrive;

import java.sql.SQLException;
import java.util.List;

public interface UserDetailsDAO {

	public abstract void addNewUser(User user);

	public abstract User getUser(String email);

}
