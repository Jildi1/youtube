package com.example.Youtube.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    protected Date timeStamp;
    protected String message;
    protected HttpStatus status;
    protected int statusCode;
    protected Map<?, ?> data;
}
