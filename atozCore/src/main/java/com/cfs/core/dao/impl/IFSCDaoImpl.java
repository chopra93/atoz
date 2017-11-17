package com.cfs.core.dao.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.entity.*;
import com.cfs.core.objects.IFSC;
import com.cfs.core.objects.Information;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
@Repository(value = "ifscDaoImpl")
public class IFSCDaoImpl implements IIFSCDao{

    private static final Logger LOG = Logger.getLogger(IFSCDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Information> fetchBankList() {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT bi.id, bi.bankName FROM BankInformation bi");
        List<Object[]> bankList = (List<Object[]>)query.list();
        return getInformationList(bankList);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Information> fetchStateListBasedOnBank(Integer bankId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT si.id, si.name FROM StateInformation si,BankStateDistrictCityIFSC bsdci WHERE si.id = bsdci.sid AND bsdci.bid =:bid");
        query.setParameter("bid", bankId);
        List<Object[]> stateList = (List<Object[]>)query.list();
        return getInformationList(stateList);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Information> fetchDistrictListBasedOnBankState(Integer bankId, Integer stateId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT di.id, di.name FROM DistrictInformation di,BankStateDistrictCityIFSC bsdci WHERE di.id = bsdci.did AND bsdci.bid =:bid AND bsdci.sid =:sid");
        query.setParameter("bid", bankId);
        query.setParameter("sid", stateId);
        List<Object[]> districtList = (List<Object[]>)query.list();
        return getInformationList(districtList);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Information> fetchCityListBasedOnBankStateDistrict(Integer bankId, Integer stateId, Integer districtId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT ci.id, ci.cityName FROM CityInformation ci,BankStateDistrictCityIFSC bsdci WHERE ci.id = bsdci.cid AND bsdci.bid =:bid AND bsdci.sid =:sid AND bsdci.did =:did");
        query.setParameter("bid", bankId);
        query.setParameter("sid", stateId);
        query.setParameter("did",districtId);
        List<Object[]> cityList = (List<Object[]>)query.list();
        return getInformationList(cityList);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public IFSC fetchBranchBasedOnBankStateDistrictCity(Integer bankId, Integer stateId, Integer districtId, Integer cityId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT bi.ifsc, bi.branch, bi.branchCode, bi.micrCode, bi.address FROM BranchInformation bi,BankStateDistrictCityIFSC bsdci WHERE bi.ifsc = bsdci.ifsc AND bsdci.bid =:bid AND bsdci.sid =:sid AND bsdci.did =:did AND bsdci.cid =:cid");
        query.setParameter("bid", bankId);
        query.setParameter("sid", stateId);
        query.setParameter("did",districtId);
        query.setParameter("cid",cityId);
        Object[] branch = (Object[]) query.uniqueResult();
        LOG.info("branch ="+branch);
        IFSC ifsc = new IFSC();

        String i = (String)branch[0];
        String bh = (String)branch[1];
        String bc = (String)branch[2];
        String mc = (String)branch[3];
        String add = (String)branch[4];

        ifsc.setIfsc(i);
        ifsc.setBranch(bh);
        ifsc.setBranchCode(bc);
        ifsc.setMicrCode(mc);
        ifsc.setAddress(add);

        return ifsc;
    }

    private List<Information> getInformationList(List<Object[]> objectList){
        List<Information> informationList = new ArrayList<>();
        for (Object[] object: objectList){
            Information information = new Information();
            Integer id = (Integer)object[0];
            String name = (String)object[1];

            information.setId(id);
            information.setName(name);
            informationList.add(information);
        }
        return informationList;
    }
}
