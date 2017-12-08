package com.cfs.api.controller;

import com.cfs.core.objects.*;
import com.cfs.service.ISMSService;
import com.opencsv.CSVReader;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.io.*;

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
        boolean response = smsService.userSignUp(user);
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

    @RequestMapping(value = "/v1/user/logout",method = RequestMethod.POST)
    public ResponseEntity userLogout(@RequestBody TokenRequest tokenRequest,
                                     @Context HttpServletRequest request){
        LogoutResponse logoutResponse = smsService.userLogout(tokenRequest.getToken());
        if (logoutResponse.getStatusCode() == 200) {
            return ResponseEntity.ok(logoutResponse);
        }
        else {
            return new ResponseEntity<>(logoutResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/v1/user/createService",method = RequestMethod.POST)
    public ResponseEntity userCreateService(@RequestBody ServiceDTO serviceDTO,
                                     @Context HttpServletRequest request){
        ServiceResponse serviceResponse = smsService.createNewService(serviceDTO);
        if (serviceResponse.getStatusCode() == 200) {
            return ResponseEntity.ok(serviceResponse);
        }
        else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/v1/user/fetchService",method = RequestMethod.POST)
    public ResponseEntity userCreateService(@RequestBody TokenRequest tokenRequest,
                                            @Context HttpServletRequest request){
        ServiceResponse serviceResponse = smsService.fetchAllActiveServices(tokenRequest.getToken());
        if (serviceResponse.getStatusCode() == 200) {
            return ResponseEntity.ok(serviceResponse);
        }
        else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/v2/user/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadMultiPart(@RequestParam(value="message",required = true) String message,
                                          @RequestParam(value="token",required = true) String token,
                                          @Context HttpServletRequest request){

        try {
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iter = upload.getItemIterator(request);
            if (!iter.hasNext()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            FileItemStream item = iter.next();
            if (StringUtils.isEmpty(message) || StringUtils.isEmpty(token)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            smsService.addMobileNumberAndMessage(token,item,message);
            return ResponseEntity.ok("");
        }
        catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("");
    }

}
