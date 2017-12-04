package com.cfs.service.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.bettercloud.vault.Vault;
import com.cfs.core.config.SNSServiceUtil;
import com.cfs.core.objects.OtpResponse;
import com.cfs.service.IOtpService;
import com.cfs.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author chopra
 * 29/11/17
 */
@Service(value = "otpServiceImpl")
public class OtpServiceImpl implements IOtpService{

    @Autowired
    private SNSServiceUtil snsServiceUtil;

    @Autowired
    private IRedisService redisService;



    @Override
    public OtpResponse sendOtp(String mobileNo) {
        OtpResponse otpResponse = new OtpResponse();
        if (mobileNo.length()!=10 || !mobileNo.matches(".*\\d+.*")){
            otpResponse.setMessage("Not a valid number");
            otpResponse.setStatusCode(500);
            return otpResponse;
        }
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String otp = String.valueOf(n);
        String message = "Welcome to ATOZ, Your OTP is "+otp;
        mobileNo = "+91"+mobileNo;

        String valueReceivedFromRedis = redisService.getValue(mobileNo);
        if (valueReceivedFromRedis !=null){
            redisService.deleteValue(mobileNo);
        }

        try {
            snsServiceUtil.sendMessage(message,mobileNo);
            redisService.setValue(mobileNo,otp,600000);
            otpResponse.setMessage("success");
            otpResponse.setStatusCode(200);

        }
        catch (Exception e){
            otpResponse.setMessage("failure");
            otpResponse.setStatusCode(500);
        }
        return otpResponse;
    }

    @Override
    public OtpResponse validateOtp(String mobileNo, String otp) {
        OtpResponse otpResponse = new OtpResponse();
        if (mobileNo.length()!=10 || !mobileNo.matches(".*\\d+.*")){
            otpResponse.setMessage("Not a valid number");
            otpResponse.setStatusCode(500);
            return otpResponse;
        }
        mobileNo = "+91"+mobileNo;
        String valueReceivedFromRedis = redisService.getValue(mobileNo);
        if (valueReceivedFromRedis == null){
            otpResponse.setMessage("First send OTP");
            otpResponse.setStatusCode(400);
        }
        if (otp.equalsIgnoreCase(valueReceivedFromRedis)){
            redisService.deleteValue(mobileNo);
            otpResponse.setMessage("Successfully verified");
            otpResponse.setStatusCode(200);
        }
        else {
            otpResponse.setMessage("Wrong OTP");
            otpResponse.setStatusCode(400);
        }
        return otpResponse;
    }
}
