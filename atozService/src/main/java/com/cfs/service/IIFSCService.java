package com.cfs.service;

import com.cfs.core.entity.*;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
public interface IIFSCService {
    List<BankInformation> fetchBankDetails();
    List<StateInformation> fetchBankDetails(Integer bankId);
    List<DistrictInformation> fetchBankDetails(Integer bankId, Integer stateId);
    List<CityInformation> fetchBankDetails(Integer bankId, Integer stateId, Integer districtId);
    BranchInformation fetchBankDetails(Integer bankId, Integer stateId, Integer districtId, Integer cityId);
}
