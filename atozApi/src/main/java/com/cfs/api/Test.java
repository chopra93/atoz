package com.cfs.api;

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
    @RequestMapping(value = "/v1/sendOtp", method = RequestMethod.GET)
    public ResponseEntity sendOtp(@RequestParam(value = "phoneNo", required = true) String phoneNo,
                                  @Context HttpServletRequest request){
        return null;
    }

}
