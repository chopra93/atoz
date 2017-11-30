package com.cfs.api.controller;

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
 * Created by Chopra on 30/11/17.
 */
@RestController
public class OTPController {

    @Autowired
    private IOtpService otpService;

    @RequestMapping(value = "/v1/sendOtp", method = RequestMethod.GET)
    public ResponseEntity sendOtp(@RequestParam(value = "mobileNo", required = true) String mobileNo,
                                  @Context HttpServletRequest request){
        OtpResponse response = otpService.sendOtp(mobileNo);
        return getResponse(response);
    }

    @RequestMapping(value = "/v1/validateOtp", method = RequestMethod.GET)
    public ResponseEntity validateOtp(@RequestParam(value = "mobileNo", required = true) String mobileNo,
                                  @RequestParam(value = "otp", required = true) String otp,
                                  @Context HttpServletRequest request){
        OtpResponse response = otpService.validateOtp(mobileNo,otp);
        return getResponse(response);
    }

    private ResponseEntity getResponse(OtpResponse response){
        int statusCode = response.getStatusCode();
        if (statusCode == 200){
            return ResponseEntity.ok(response);
        }
        else
        if (statusCode == 400){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
