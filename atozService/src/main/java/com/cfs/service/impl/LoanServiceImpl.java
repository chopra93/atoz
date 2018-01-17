package com.cfs.service.impl;

import com.cfs.constant.AtozConstants;
import com.cfs.core.dao.ILoanDao;
import com.cfs.core.entity.LoanUsers;
import com.cfs.core.enums.ServiceEnum;
import com.cfs.core.objects.LoanRequest;
import com.cfs.core.objects.LoanResponse;
import com.cfs.core.objects.PublishMsgRequest;
import com.cfs.core.objects.UserDTO;
import com.cfs.service.ILoanService;
import com.cfs.service.IPublishService;
import com.cfs.service.IRedisService;
import com.cfs.service.ISMSService;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;

/**
 * @author chopra
 * 17/01/18
 */
@Service
public class LoanServiceImpl implements ILoanService{
    @Autowired
    private ILoanDao loanDao;

    @Autowired
    private IPublishService publishService;

    @Autowired
    private IRedisService redisService;

    private static final Gson GSON = new Gson();


    @Override
    public LoanResponse applyForLoan(LoanRequest loanRequest) {
        LoanResponse loanResponse = new LoanResponse();
        if (loanRequest!= null && isValidNumber(loanRequest.getPhone()) && isValidAmount(loanRequest.getLoanAmount()) && loanRequest.getPreferenceOne() != null && loanRequest.getType()!=null && loanRequest.getEmail()!= null && loanRequest.getName()!= null){

            PublishMsgRequest publishMsgRequest = new PublishMsgRequest();
            publishMsgRequest.setType(ServiceEnum.OTP);
            publishMsgRequest.setMobileNo(loanRequest.getPhone());
            publishMsgRequest.setMessage("#PIN# is your OTP to apply for loan");
            publishMsgRequest.setToken(AtozConstants.DEFAULT_TOKEN);

            publishService.sendOtp(publishMsgRequest);
            redisService.setValue("LOAN-INFO-+91"+loanRequest.getPhone(),GSON.toJson(loanRequest));

            loanResponse.setMessage("Enter OTP to validate and save Loan information");
            loanResponse.setStatusCode(HttpStatus.SC_OK);

        }
        else {
            loanResponse.setMessage("Please enter valid information");
            loanResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        }
        return loanResponse;
    }

    @Override
    public LoanResponse validateOTPAndSaveInformation(String phoneNumber, String otp){
        LoanResponse loanResponse = new LoanResponse();
        String valueJsonLoanRequest = redisService.getValue("LOAN-INFO-+91"+phoneNumber);
        LoanRequest loanRequest = GSON.fromJson(valueJsonLoanRequest, LoanRequest.class);

        LoanUsers loanUsers = loanDao.userExist(loanRequest.getPhone());
        loanDao.insertLoanInformation(loanRequest,loanUsers);
        loanResponse.setMessage("Loan Information Saved");
        loanResponse.setStatusCode(HttpStatus.SC_OK);
        return loanResponse;
    }

    private boolean isValidNumber(String phoneNumber){
        if(phoneNumber!= null && phoneNumber.matches("[0-9]+") && phoneNumber.length() == 10){
            return true;
        }
        return false;

    }

    private boolean isValidAmount(Integer amount){
        if(amount<10000)
            return false;
        return true;
    }
}
