package com.cfs.api.controller;

import com.cfs.core.commons.OAuthVerify;
import com.cfs.core.enums.ServiceEnum;
import com.cfs.core.objects.PublishMsgRequest;
import com.cfs.core.objects.PublishResponse;
import com.cfs.core.objects.TokenRequest;
import com.cfs.service.IPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * Created by Chopra on 30/11/17.
 */
@RestController
public class PublishController {

    @Autowired
    private IPublishService otpService;

    @RequestMapping(value = "/v1/sendSMS", method = RequestMethod.POST)
    public ResponseEntity sendOtp(@RequestBody PublishMsgRequest publishMsgRequest,
                                  @Context HttpServletRequest request){
        if (StringUtils.isEmpty(publishMsgRequest) || StringUtils.isEmpty(publishMsgRequest.getMessage()) || StringUtils.isEmpty(publishMsgRequest.getMobileNo()) || StringUtils.isEmpty(publishMsgRequest.getToken())|| StringUtils.isEmpty(publishMsgRequest.getType())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PublishResponse response = otpService.sendOtp(publishMsgRequest);
        return getResponse(response);
    }

    @RequestMapping(value = "/v1/validateOtp", method = RequestMethod.GET)
    public ResponseEntity validateOtp(@RequestParam(value = "mobileNo", required = true) String mobileNo,
                                  @RequestParam(value = "otp", required = true) String otp,
                                  @Context HttpServletRequest request){
        PublishResponse response = otpService.validateOtp(mobileNo,otp);
        return getResponse(response);
    }

    private ResponseEntity getResponse(PublishResponse response){
        if(response == null){
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
