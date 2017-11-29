package com.cfs.service;

import com.cfs.core.objects.OtpResponse;

/**
 * @author chopra
 * 29/11/17
 */
public interface IOtpService {
    OtpResponse sendOtp(String mobileNo);
}
