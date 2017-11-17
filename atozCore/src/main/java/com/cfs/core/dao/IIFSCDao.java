package com.cfs.core.dao;

import com.cfs.core.entity.*;
import com.cfs.core.objects.IFSC;
import com.cfs.core.objects.Information;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
public interface IIFSCDao {
    List<Information> fetchBankList();
    List<Information> fetchStateListBasedOnBank(Integer bankId);
    List<Information> fetchDistrictListBasedOnBankState(Integer bankId, Integer stateId);
    List<Information> fetchCityListBasedOnBankStateDistrict(Integer bankId, Integer stateId, Integer districtId);
    List<IFSC> fetchBranchBasedOnBankStateDistrictCity(Integer bankId, Integer stateId, Integer districtId, Integer cityId);
}
