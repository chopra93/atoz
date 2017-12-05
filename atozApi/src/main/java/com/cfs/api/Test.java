package com.cfs.api;

import com.cfs.core.entity.Users;
import com.cfs.core.objects.OtpResponse;
import com.cfs.service.IOtpService;
import com.cfs.service.ISMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author chopra
 * 12/11/17
 */
@RestController
public class Test {


    @Autowired
    private ISMSService smsService;

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity test(){
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/v1/user",method = RequestMethod.GET)
    public ResponseEntity testUsername(@RequestParam(value = "username", required = false) String username,
                                       @Context HttpServletRequest request){
        boolean response = smsService.isUniqueUsername(username);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/v1/user/insert",method = RequestMethod.GET)
    public ResponseEntity testUsernameInsert(@RequestBody Users user,
                                       @Context HttpServletRequest request){
        boolean response = smsService.insertUser(user);
        return ResponseEntity.ok(response);
    }
}
