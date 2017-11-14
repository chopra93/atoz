package com.cfs.api.controller;

import com.cfs.core.entity.BankInformation;
import com.cfs.service.IIFSCService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * @author chopra
 * 11/11/17
 */
@RestController
public class IFSCController {

    private static final Logger LOG = Logger.getLogger(IFSCController.class);
    @Autowired
    private IIFSCService ifscService;

    @RequestMapping(value = "/v1/test",method = RequestMethod.GET)
    public ResponseEntity TestFunction(@Context HttpServletRequest request){
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/v1/fetchBankList", method = RequestMethod.GET)
    public ResponseEntity fetchBankList(@Context HttpServletRequest request){
        LOG.info("Inside IFSC Controller");
        List<BankInformation> bankList  = ifscService.fetchBankDetails();
        return ResponseEntity.ok(bankList);
    }
}
