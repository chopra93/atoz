package com.cfs.service;

import com.cfs.core.objects.LoanRequest;
import com.cfs.core.objects.LoanResponse;

/**
 * @author chopra
 * 17/01/18
 */
public interface ILoanService {
    LoanResponse applyForLoan(LoanRequest loanRequest);
    LoanResponse validateOTPAndSaveInformation(String phoneNumber, String otp);
}
