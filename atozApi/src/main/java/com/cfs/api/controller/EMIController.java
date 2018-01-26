package com.cfs.api.controller;

import com.cfs.core.enums.CompoundingEnum;
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
import java.math.BigDecimal;

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
        Long res = emiService.calculateEMI(p,r,t,type);
        BigDecimal b = BigDecimal.valueOf(res);
        Integer month = (type==YearMonth.YEAR)?(12*t):t;

        emiResponse.setAmount((new BigDecimal(p)).toString());
        emiResponse.setRate(Double.toString(r));
        emiResponse.setDuration(Double.toString(month));
        emiResponse.setEmi(b.toString());

        return ResponseEntity.ok(emiResponse);
    }

    @RequestMapping(value = "/v1/fd", method = RequestMethod.GET)
    public ResponseEntity fetchFD(@RequestParam(required = true, value = "p") Double p,
                                  @RequestParam(required = true, value = "r") Double r,
                                  @RequestParam(required = true, value = "t") Integer t,
                                  @RequestParam(required = true, value = "type") YearMonth type,
                                  @RequestParam(required = true, value = "cType") CompoundingEnum cType,
                                  @Context HttpServletRequest request){
        EMIResponse emiResponse = new EMIResponse();
        Long res = emiService.calculateFDInterest(p,r,t,type,cType);
        BigDecimal b = BigDecimal.valueOf(res);


        Integer month = (type==YearMonth.YEAR)?t*12:t;
        Integer duration = emiService.getValueInYear(cType);

        emiResponse.setAmount((new BigDecimal(p)).toString());
        emiResponse.setRate(Double.toString(r));
        emiResponse.setDuration(Double.toString(month));
        emiResponse.setFd(b.toString());
        emiResponse.setCoumpoundFrequency(Double.toString(duration));

        return ResponseEntity.ok(emiResponse);
    }

    @RequestMapping(value = "/v1/rd", method = RequestMethod.GET)
    public ResponseEntity fetchRD(@RequestParam(required = true, value = "p") Double p,
                                  @RequestParam(required = true, value = "r") Double r,
                                  @RequestParam(required = true, value = "t") Integer t,
                                  @RequestParam(required = true, value = "type") YearMonth type,
                                  @RequestParam(required = true, value = "cType") CompoundingEnum cType,
                                  @Context HttpServletRequest request){
        EMIResponse emiResponse = new EMIResponse();
        Long res = emiService.calculateRDInterest(p,r,t,type,cType);
        BigDecimal b = BigDecimal.valueOf(res);

        Integer month = (type==YearMonth.YEAR)?(12*t):t;
        Integer duration = emiService.getValueInYear(cType);

        emiResponse.setAmount((new BigDecimal(p)).toString());
        emiResponse.setRate(Double.toString(r));
        emiResponse.setDuration(Double.toString(month));
        emiResponse.setRd(b.toString());
        emiResponse.setCoumpoundFrequency(Double.toString(duration));

        return ResponseEntity.ok(emiResponse);
    }
}
