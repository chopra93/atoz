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
import com.cfs.core.objects.OtpResponse;
import com.cfs.service.IOtpService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chopra
 * 29/11/17
 */
@Service
public class OtpServiceImpl implements IOtpService{

    private AmazonSNS sns;

    @Override
    public OtpResponse sendOtp(String mobileNo) {
        if (mobileNo.length()!=10 || !mobileNo.matches(".*\\d+.*")){
            return null;
        }

        OtpResponse otpResponse = new OtpResponse();
        String accesskeyId = "AKIAJLFJEQP6F64B25SA";
        String secretkey = "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";
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesskeyId, secretkey);

        String message = "Welcome to atoz";
        mobileNo = "+91"+mobileNo;

        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

        sns = AmazonSNSClient.builder().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_WEST_2).build();   ;//new AmazonSNSClient();
        PublishResult result = sns.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(mobileNo).withMessageAttributes(smsAttributes));

        otpResponse.setMessage("success");
        otpResponse.setStatusCode(200);
        return otpResponse;
    }
}
