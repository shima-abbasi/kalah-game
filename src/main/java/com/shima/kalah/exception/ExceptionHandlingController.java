package com.shima.kalah.exception;

import com.shima.kalah.dto.KalahApiDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
@RestController
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler(KalahException.class)
    public ResponseEntity<KalahApiDTO> handleApiException(KalahException ex, HttpServletRequest request) {
        return exceptionToHttpResponse(ex, HttpStatus.BAD_REQUEST,request);
    }
    private ResponseEntity<KalahApiDTO> exceptionToHttpResponse(KalahException ex, HttpStatus status, HttpServletRequest request){
        logger.log(Level.SEVERE,ex.getClass().getSimpleName() + " thrown with message = " + ex.getMessage() + " for service " + request.getServletPath());
        return exceptionToHttpResponse(ex, ex.getMessage(), status);
    }

    private ResponseEntity<KalahApiDTO> exceptionToHttpResponse(KalahException ex, String message, HttpStatus status) {
        KalahApiDTO response = new KalahApiDTO(Boolean.TRUE, message , null);
        return new ResponseEntity<>(response, status);
    }
}
