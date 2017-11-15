package com.cfs.core.dao;

import com.cfs.core.entity.*;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
public interface IIFSCDao {
    List<BankInformation> fetchBankList();
    List<StateInformation> fetchStateListBasedOnBank(Integer bankId);
    List<DistrictInformation> fetchDistrictListBasedOnBankState(Integer bankId, Integer stateId);
    List<CityInformation> fetchCityListBasedOnBankStateDistrict(Integer bankId, Integer stateId, Integer districtId);
    BranchInformation fetchBranchBasedOnBankStateDistrictCity(Integer bankId, Integer stateId, Integer districtId, Integer cityId);
}
