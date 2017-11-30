package com.cfs.api;

import com.cfs.core.objects.OtpResponse;
import com.cfs.service.IOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author chopra
 * 12/11/17
 */
@RestController
public class Test {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity test(){
        return ResponseEntity.ok("ok");
    }

}
