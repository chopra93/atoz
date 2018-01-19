package com.cfs.config;

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
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chopra on 30/11/17.
 */
@DependsOn("vaultSetup")
@Component("snsServiceUtil")
public class SNSServiceUtil {

    private static final Logger LOG = Logger.getLogger(SNSServiceUtil.class);

    private final Vault vault;

    private AmazonSNS amazonSNS;

    private String accesskeyId;
    private String secretkey;
    private Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();


    @Autowired
    public SNSServiceUtil(Vault vault){
        this.vault = vault;
        init();
    }

    private void init(){
        try {
            LOG.info("loading sns configuration");
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

    public void sendMessage(String message,String mobileNo){
        amazonSNS.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(mobileNo).withMessageAttributes(smsAttributes));
    }
}
