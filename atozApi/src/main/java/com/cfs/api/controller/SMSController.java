package com.cfs.api.controller;

import com.cfs.core.objects.LoginDTO;
import com.cfs.core.objects.LoginResponse;
import com.cfs.core.objects.UserDTO;
import com.cfs.service.ISMSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author chopra
 * 06/12/17
 */
@RestController
public class SMSController {
    private static final Logger LOG = Logger.getLogger(SMSController.class);

    @Autowired
    private ISMSService smsService;

    @RequestMapping(value = "/v1/user",method = RequestMethod.GET)
    public ResponseEntity testUsername(@RequestParam(value = "username", required = false) String username,
                                       @Context HttpServletRequest request){
        boolean response = smsService.isUniqueUsername(username);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/v1/user/signUp",method = RequestMethod.POST)
    public ResponseEntity testUsernameInsert(@RequestBody UserDTO user,
                                             @Context HttpServletRequest request){
        boolean response = smsService.insertUser(user);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/v1/user/login",method = RequestMethod.POST)
    public ResponseEntity userLogin(@RequestBody LoginDTO userLogin,
                                    @Context HttpServletRequest request){
        LoginResponse loginResponse = smsService.userLogin(userLogin);
        if (loginResponse == null){
            return new ResponseEntity<>(loginResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (200 == loginResponse.getStatus()){
            return ResponseEntity.ok(loginResponse);
        }
        else {
            return new ResponseEntity<>(loginResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
