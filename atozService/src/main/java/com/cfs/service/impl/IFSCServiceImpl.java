package com.cfs.service.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.objects.IFSC;
import com.cfs.core.objects.Information;
import com.cfs.service.IIFSCService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author chopra
 * 12/11/17
 */
@Service(value = "ifscServiceImpl")
public class IFSCServiceImpl implements IIFSCService{

    private static final Logger LOG = Logger.getLogger(IFSCServiceImpl.class);

    @Autowired
    private IIFSCDao ifscDao;

    private Set<Information> fetchBankDetails() {
        List<Information> informationList = ifscDao.fetchBankList();
        return new TreeSet<>(informationList);
    }

    private Set<Information> fetchBankDetails(Integer bankId){
        List<Information> informationList = ifscDao.fetchStateListBasedOnBank(bankId);
        return new TreeSet<>(informationList);
    }

    private Set<Information> fetchBankDetails(Integer bankId, Integer stateId){
        List<Information> informationList = ifscDao.fetchDistrictListBasedOnBankState(bankId,stateId);
        return new TreeSet<>(informationList);
    }

    private Set<Information> fetchBankDetails(Integer bankId, Integer stateId, Integer districtId){
        List<Information> informationList = ifscDao.fetchCityListBasedOnBankStateDistrict(bankId,stateId,districtId);
        return new TreeSet<>(informationList);
    }

    @Override
    @Transactional
    public Set<Information> BankDetails(Integer bankId, Integer stateId, Integer districtId) {
        if (bankId != null && stateId != null && districtId != null){
            return fetchBankDetails(bankId,stateId,districtId);
        }
        else
        if (bankId != null && stateId != null){
            return fetchBankDetails(bankId,stateId);
        }
        else
        if (bankId != null){
            return fetchBankDetails(bankId);
        }
        else {
            return fetchBankDetails();
        }
    }

    @Override
    @Transactional
    public List<IFSC> BankDetails(Integer bankId, Integer stateId, Integer districtId, Integer cityId){
        List<IFSC> ifscList = ifscDao.fetchBranchBasedOnBankStateDistrictCity(bankId,stateId,districtId,cityId);
        Collections.sort(ifscList);
        return ifscList;
    }
}
