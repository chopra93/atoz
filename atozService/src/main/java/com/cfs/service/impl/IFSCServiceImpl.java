package com.cfs.service.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.entity.*;
import com.cfs.service.IIFSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
@Service(value = "ifscServiceImpl")
public class IFSCServiceImpl implements IIFSCService{
    @Autowired
    private IIFSCDao ifscDao;

    @Override
    @Transactional
    public List<BankInformation> fetchBankDetails() {
        return ifscDao.fetchBankList();
    }

    @Override
    @Transactional
    public List<StateInformation> fetchBankDetails(Integer bankId){
        return ifscDao.fetchStateListBasedOnBank(bankId);
    }

    @Override
    @Transactional
    public List<DistrictInformation> fetchBankDetails(Integer bankId, Integer stateId){
        return ifscDao.fetchDistrictListBasedOnBankState(bankId,stateId);
    }

    @Override
    @Transactional
    public List<CityInformation> fetchBankDetails(Integer bankId, Integer stateId, Integer districtId){
        return ifscDao.fetchCityListBasedOnBankStateDistrict(bankId,stateId,districtId);
    }

    @Override
    @Transactional
    public BranchInformation fetchBankDetails(Integer bankId, Integer stateId, Integer districtId, Integer cityId){
        return ifscDao.fetchBranchBasedOnBankStateDistrictCity(bankId,stateId,districtId,cityId);
    }
}
