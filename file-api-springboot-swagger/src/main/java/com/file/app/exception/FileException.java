package com.file.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.file.app.model.ResponseMsg;

@ControllerAdvice
public class FileException extends ResponseEntityExceptionHandler  {
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	  public ResponseEntity<ResponseMsg> handleMaxSizeException(MaxUploadSizeExceededException exc) {
	    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMsg("File too large!"));
	  }
}
