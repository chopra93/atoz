package com.cfs.api.controller;

import com.cfs.core.enums.YearMonth;
import com.cfs.core.objects.EMIResponse;
import com.cfs.service.IEMIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * Created by Chopra on 15/11/17.
 */
@RestController
public class EMIController {

    @Autowired
    private IEMIService emiService;

    @RequestMapping(value = "/v1/emi", method = RequestMethod.GET)
    public ResponseEntity fetchEMI(@RequestParam(required = true, value = "p") Double p,
                                   @RequestParam(required = true, value = "r") Double r,
                                   @RequestParam(required = true, value = "t") Integer t,
                                   @RequestParam(required = true, value = "type") YearMonth type,
                                   @Context HttpServletRequest request){
        EMIResponse emiResponse = new EMIResponse();
        double res = emiService.calculateEMI(p,r,t,type);
        emiResponse.setEmi(Double.toString(res));
        return ResponseEntity.ok(emiResponse);
    }
}
