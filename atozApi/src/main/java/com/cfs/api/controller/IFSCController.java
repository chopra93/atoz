package com.cfs.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author chopra
 * 11/11/17
 */
@RestController
public class IFSCController {

    @RequestMapping(value = "/v1/test",method = RequestMethod.GET)
    public ResponseEntity TestFunction(@Context HttpServletRequest request){
        return ResponseEntity.ok("ok");
    }
}
