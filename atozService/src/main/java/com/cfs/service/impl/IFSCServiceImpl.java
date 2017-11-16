package com.cfs.service.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.entity.*;
import com.cfs.core.objects.Information;
import com.cfs.service.IIFSCService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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

    @Transactional
    private Set<Information> fetchBankDetails() {
        List<BankInformation> bankInformationList = ifscDao.fetchBankList();

        Set<Information> informationSet = new TreeSet<>();
        for (BankInformation bankInformation:bankInformationList){
            Information information = new Information();
            information.setId(bankInformation.getId());
            information.setName(bankInformation.getBankName());
            informationSet.add(information);
        }
        return informationSet;
    }

    @Transactional
    private Set<Information> fetchBankDetails(Integer bankId){
        List<StateInformation> stateInformationList = ifscDao.fetchStateListBasedOnBank(bankId);

        Set<Information> informationSet = new TreeSet<>();
        for (StateInformation stateInformation:stateInformationList){
            Information information = new Information();
            information.setId(stateInformation.getId());
            information.setName(stateInformation.getName());
            informationSet.add(information);
        }
        return informationSet;
    }

    @Transactional
    private Set<Information> fetchBankDetails(Integer bankId, Integer stateId){
        List<DistrictInformation> districtInformationList = ifscDao.fetchDistrictListBasedOnBankState(bankId,stateId);

        Set<Information> informationSet = new TreeSet<>();
        for (DistrictInformation districtInformation:districtInformationList){
            Information information = new Information();
            information.setId(districtInformation.getId());
            information.setName(districtInformation.getName());
            informationSet.add(information);
        }
        return informationSet;
    }

    @Transactional
    private Set<Information> fetchBankDetails(Integer bankId, Integer stateId, Integer districtId){
        List<CityInformation> cityInformationList = ifscDao.fetchCityListBasedOnBankStateDistrict(bankId,stateId,districtId);

        Set<Information> informationSet = new TreeSet<>();
        for (CityInformation cityInformation:cityInformationList){
            Information information = new Information();
            information.setId(cityInformation.getId());
            information.setName(cityInformation.getCityName());
            informationSet.add(information);
        }
        return informationSet;
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
    public BranchInformation BankDetails(Integer bankId, Integer stateId, Integer districtId, Integer cityId){
        return ifscDao.fetchBranchBasedOnBankStateDistrictCity(bankId,stateId,districtId,cityId);
    }
}
