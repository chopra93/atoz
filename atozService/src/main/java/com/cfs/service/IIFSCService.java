package com.cfs.service;

import com.cfs.core.entity.BankInformation;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
public interface IIFSCService {
    List<BankInformation> fetchBankDetails();
}
