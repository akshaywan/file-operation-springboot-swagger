package com.file.app.daoImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.file.app.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	private final Path root = Paths.get("storage");
	@Override
	public void initDir() {
		try {
		      Files.createDirectory(root);
		    } catch (IOException e) {
		      throw new RuntimeException("Could not initialize folder for upload!");
		    }
	}


	@Override
	public String uploadFile(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return "File uploaded successfully:";
	}

	@Override
	public Resource downloandFile(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public String deleteFile() {
		FileSystemUtils.deleteRecursively(root.toFile());
		return "file deleted successfully.";
	}

	@Override
	public String createFile(MultipartFile file) {

		String filename = file.getOriginalFilename();
		System.out.println(filename);
		String[] fileNameArr = filename.split("\\.");
		String newName = fileNameArr[0] + "copy." + fileNameArr[1];
		try {
			Files.copy(file.getInputStream(), this.root.resolve(newName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return "Copy of file created successfully with name " + newName;
	}


}
