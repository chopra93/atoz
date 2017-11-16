package com.cfs.api.controller;

import com.cfs.core.entity.*;
import com.cfs.core.objects.Information;
import com.cfs.service.IIFSCService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(value = "/v1/fetchBankDetail", method = RequestMethod.GET)
    public ResponseEntity fetchBankDetails(@RequestParam(value = "bankId", required = false) Integer bankId,
                                           @RequestParam(value = "stateId", required = false) Integer stateId,
                                           @RequestParam(value = "districtId", required = false) Integer districtId,
                                           @RequestParam(value = "cityId", required = false) Integer cityId,
                                           @Context HttpServletRequest request){

        LOG.info("Inside IFSC Controller");
        Set<Information> informationSet = null;
        BranchInformation branchInformation = null;
        if (bankId !=null && stateId !=null && districtId != null && cityId != null){
            branchInformation = fetchBranchInformation(bankId,stateId,districtId,cityId);
            if (branchInformation != null){
                return ResponseEntity.ok(branchInformation);
            }
            else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        else
        if (bankId !=null && stateId !=null && districtId != null){
            informationSet = fetchCityList(bankId,stateId,districtId);
        }
        else
        if (bankId !=null && stateId !=null ){
            informationSet = fetchDistrictList(bankId,stateId);
        }
        else
        if (bankId !=null ){
            informationSet = fetchStateList(bankId);
        }
        else {
            informationSet = fetchBankList();
        }

        if (informationSet!=null){
            return ResponseEntity.ok(informationSet);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Set<Information> fetchBankList(){
        Set<Information> bankSet  = ifscService.BankDetails(null,null,null);
        return bankSet;
    }

    private Set<Information> fetchStateList(Integer bankId){
        Set<Information> stateSet  = ifscService.BankDetails(bankId,null,null);
        return stateSet;
    }

    private Set<Information> fetchDistrictList(Integer bankId, Integer stateId){
        Set<Information> districtSet  = ifscService.BankDetails(bankId,stateId,null);
        return districtSet;
    }

    private Set<Information> fetchCityList(Integer bankId, Integer stateId, Integer districtId){
        Set<Information> citySet = ifscService.BankDetails(bankId,stateId,districtId);
        return citySet;
    }

    private BranchInformation fetchBranchInformation(Integer bankId, Integer stateId, Integer districtId, Integer cityId){
        BranchInformation branchInformation = ifscService.BankDetails(bankId,stateId,districtId,cityId);
        return branchInformation;
    }
}
