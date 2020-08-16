package com.file.app.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public void initDir();
	public String uploadFile(MultipartFile file);
	public Resource downloandFile(String filename);
	public String deleteFile();
	public String createFile(MultipartFile file);
}
