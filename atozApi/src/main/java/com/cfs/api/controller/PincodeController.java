package com.cfs.api.controller;

import com.cfs.core.objects.PincodeInforamtionDTO;
import com.cfs.service.IPincodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Chopra on 26/11/17.
 */
@RestController
public class PincodeController {

    private static final Logger LOG = Logger.getLogger(PincodeController.class);

    @Autowired
    private IPincodeService pincodeService;

    @RequestMapping(value = "/v1/pincode", method = RequestMethod.GET)
    public ResponseEntity fetchPincodeInformationBasedOnPincode(@RequestParam(value = "pincode", required = true) String pincode,
                                                                @Context HttpServletRequest request){
        PincodeInforamtionDTO pincodeInforamtionDTO = pincodeService.getPincodeInformationBasedOnPincode(pincode);
        if (pincodeInforamtionDTO == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(pincodeInforamtionDTO);
    }

    @RequestMapping(value = "/v1/AllPincode", method = RequestMethod.GET)
    public ResponseEntity fetchListOfPincode(@RequestParam(value = "state", required = true) String state,
                                             @RequestParam(value = "city", required = true) String city,
                                                                @Context HttpServletRequest request){
        List<String> pincodeList = pincodeService.getPincodeBasedOnStateAndCity(state,city);
        if (pincodeList == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(pincodeList);
    }

}
