package com.cfs.core.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chopra on 30/11/17.
 */
@Component
public final class SNSServiceUtil {

    private static volatile SNSServiceUtil snsInstance = null;
    private static AmazonSNS amazonSNS;
    private static String accesskeyId = "AKIAJLFJEQP6F64B25SA";
    private static String secretkey = "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";
    private static Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();


    private SNSServiceUtil(){
        init();
    }

    private static void init(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesskeyId, secretkey);
        amazonSNS = AmazonSNSClient.builder().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_WEST_2).build();
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));
    }

    public static SNSServiceUtil getInstance(){
        if (snsInstance == null){
            synchronized (SNSServiceUtil.class){
                if (snsInstance == null){
                    snsInstance = new SNSServiceUtil();
                }
            }
        }
        return snsInstance;
    }

    public void sendMessage(String message,String mobileNo){
        amazonSNS.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(mobileNo).withMessageAttributes(smsAttributes));
    }


}
