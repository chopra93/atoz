package com.cfs.core.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.cfs.core.vault.VaultConstants;
import com.cfs.core.vault.VaultProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chopra on 30/11/17.
 */
@Component
public final class SNSServiceUtil {

    private static final Logger LOG = Logger.getLogger(SNSServiceUtil.class);

    @Autowired
    private Vault vault;

    private static volatile SNSServiceUtil snsInstance = null;
    private static AmazonSNS amazonSNS;
    /*
    dbCredentials.url = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_MYSQL_DB_URL);

     */

    private String accesskeyId;
    private String secretkey;
    private Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();


    private SNSServiceUtil(){
        init();
    }

    private void init(){
        try {
            accesskeyId = vault.logical().read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE).getData().get(VaultConstants.VAULT_ACCESS_KEY_ID);
            secretkey = vault.logical().read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE).getData().get(VaultConstants.VAULT_SECRET_KEY);
            LOG.info("accesskeyId:secretkey"+accesskeyId+":"+secretkey);
        } catch (VaultException e) {
            LOG.error("failed to load accessKeyId and secret KEY");
        }

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
