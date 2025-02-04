package com.novaes.treinamentos.user;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class ModelNotFoundException extends RuntimeException{
	
	ModelNotFoundException(Resource resource){
		super("Model not found: "+resource.getFilename());
	}

}
