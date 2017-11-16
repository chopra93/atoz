package com.cfs.api.controller;

import com.cfs.core.entity.*;
import com.cfs.service.IIFSCService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/v1/fetchStateList", method = RequestMethod.GET)
    public ResponseEntity fetchStateList(@RequestParam(value = "bankId", required = true) Integer bankId,
                                         @Context HttpServletRequest request){
        LOG.info("Inside IFSC Controller");
        List<StateInformation> stateList  = ifscService.fetchBankDetails(bankId);
        return ResponseEntity.ok(stateList);
    }

    @RequestMapping(value = "/v1/fetchDistrictList", method = RequestMethod.GET)
    public ResponseEntity fetchDistrictList(@RequestParam(value = "bankId", required = true) Integer bankId,
                                            @RequestParam(value = "stateId", required = true) Integer stateId,
                                         @Context HttpServletRequest request){
        LOG.info("Inside IFSC Controller");
        List<DistrictInformation> districtList  = ifscService.fetchBankDetails(bankId,stateId);
        return ResponseEntity.ok(districtList);
    }

    @RequestMapping(value = "/v1/fetchCityList", method = RequestMethod.GET)
    public ResponseEntity fetchCityList(@RequestParam(value = "bankId", required = true) Integer bankId,
                                        @RequestParam(value = "stateId", required = true) Integer stateId,
                                        @RequestParam(value = "districtId", required = true) Integer districtId,
                                         @Context HttpServletRequest request){
        LOG.info("Inside IFSC Controller");
        List<CityInformation> cityList  = ifscService.fetchBankDetails(bankId,stateId,districtId);
        return ResponseEntity.ok(cityList);
    }

    @RequestMapping(value = "/v1/fetchBranchInformation", method = RequestMethod.GET)
    public ResponseEntity fetchBranchInformation(@RequestParam(value = "bankId", required = true) Integer bankId,
                                                 @RequestParam(value = "stateId", required = true) Integer stateId,
                                                 @RequestParam(value = "districtId", required = true) Integer districtId,
                                                 @RequestParam(value = "cityId", required = true) Integer cityId,
                                                 @Context HttpServletRequest request){
        LOG.info("Inside IFSC Controller");
        BranchInformation branchInformation = ifscService.fetchBankDetails(bankId,stateId,districtId,cityId);
        return ResponseEntity.ok(branchInformation);
    }
}
