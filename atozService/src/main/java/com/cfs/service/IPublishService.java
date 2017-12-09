package com.cfs.service;

import com.cfs.core.enums.ServiceEnum;
import com.cfs.core.objects.PublishMsgRequest;
import com.cfs.core.objects.PublishResponse;

/**
 * @author chopra
 * 29/11/17
 */
public interface IPublishService {
    PublishResponse sendOtp(PublishMsgRequest publishMsgRequest);
    PublishResponse validateOtp(String mobileNo, String otp);
}
