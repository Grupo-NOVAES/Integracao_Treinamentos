package com.novaes.treinamentos.NR;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NrNotFoundException extends RuntimeException{

}
