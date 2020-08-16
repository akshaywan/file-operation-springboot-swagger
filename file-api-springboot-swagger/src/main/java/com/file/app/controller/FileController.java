package com.file.app.controller;

import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.app.model.ResponseMsg;
import com.file.app.service.FileService;

@RestController
public class FileController {
	public static final Logger logger = LoggerFactory.getLogger(FileController.class);
	@Autowired
	FileService fileService;
	@PostMapping("/api/upload")
	public ResponseEntity<ResponseMsg> uploadFile(@RequestParam("file") MultipartFile file) {
		logger.debug("inside upload docs");
		String message = "";
		try {
			String response = fileService.uploadFile(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			logger.debug("inside upload docs response"+response+" "+message);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMsg(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMsg(message));
		}
	}
	
	@GetMapping("/api/download/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		logger.debug("inside upload getfile");
		Resource file = fileService.downloandFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/api/deleteFile")
	@ResponseBody
	public ResponseEntity<ResponseMsg> deleteFile() {
		logger.debug("inside upload getfile");
		String file = fileService.deleteFile();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMsg(file));
	}
	
	@PostMapping("/api/createFile")
	public ResponseEntity<ResponseMsg> createFile(@RequestParam("file") MultipartFile file) {
		logger.debug("inside create file");
		String message = "";
		try {
			String response1 = fileService.uploadFile(file);
			String response2 = fileService.createFile(file);
			message =response1 + file.getOriginalFilename()+" and "+response2;
			logger.debug("inside upload docs response"+response2+" "+message);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMsg(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMsg(message));
		}
	}
}
