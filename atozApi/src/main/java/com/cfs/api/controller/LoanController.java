package com.cfs.api.controller;

import com.cfs.core.objects.LoanRequest;
import com.cfs.core.objects.LoanResponse;
import com.cfs.service.ILoanService;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author chopra
 * 17/01/18
 */
@RestController
public class LoanController {

    private static final Logger LOG = Logger.getLogger(LoanController.class);

    @Autowired
    private ILoanService loanService;

    @RequestMapping(value = "/v1/applyloan", method = RequestMethod.POST)
    private ResponseEntity applyLoan(@RequestBody LoanRequest loanRequest,
                                     @Context HttpServletRequest request){
        LoanResponse loanResponse = loanService.applyForLoan(loanRequest);
        if (loanResponse.getStatusCode() == HttpStatus.SC_OK){
            return ResponseEntity.ok(loanResponse);
        }
        else
        if (loanResponse.getStatusCode() == HttpStatus.SC_BAD_REQUEST)
        {
            return new ResponseEntity<>(loanResponse, org.springframework.http.HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(loanResponse, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/v1/applyloan/validate", method = RequestMethod.GET)
    private ResponseEntity validateAndApplyForLoan(@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                   @RequestParam(value = "otp", required = true) String otp){
        LoanResponse loanResponse = loanService.validateOTPAndSaveInformation(phoneNumber,otp);
        return ResponseEntity.ok(loanResponse);

    }

}
