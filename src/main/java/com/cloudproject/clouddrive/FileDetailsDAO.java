package com.cloudproject.clouddrive;

import java.util.List;

public interface FileDetailsDAO {

	public abstract void addNewfile(String email, String filename, String Desc);

	public abstract List<FileUpload> getFileDetails(String email);
	
	public abstract void deleteFileDetails(String email, String fileName);

}
