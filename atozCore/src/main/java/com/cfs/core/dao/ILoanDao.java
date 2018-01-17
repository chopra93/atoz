package com.cfs.core.dao;

import com.cfs.core.entity.LoanUsers;
import com.cfs.core.objects.LoanRequest;

/**
 * @author chopra
 * 17/01/18
 */
public interface ILoanDao {
    LoanUsers userExist(String phoneNumber);
    boolean insertLoanInformation(LoanRequest loanRequest,LoanUsers loanUsers);
}
