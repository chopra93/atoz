package com.cfs.service.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.cfs.core.objects.OtpResponse;
import com.cfs.service.IOtpService;

/**
 * @author chopra
 * 29/11/17
 */
public class OtpServiceImpl implements IOtpService{
    @Override
    public OtpResponse sendOtp(String mobileNo) {
        if (mobileNo.length()!=10 || !mobileNo.matches(".*\\d+.*")){
//            return null;
        }

        String accesskeyId = "AKIAJLFJEQP6F64B25SA";
        String secretkey = "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";
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesskeyId, secretkey);



        AmazonSNS sns = AmazonSNSClient.builder().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.AP_SOUTH_1).build();   ;//new AmazonSNSClient();
        return null;
    }
}
