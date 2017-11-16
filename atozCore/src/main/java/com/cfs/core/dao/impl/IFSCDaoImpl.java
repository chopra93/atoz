package com.cfs.core.dao.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
@Repository(value = "ifscDaoImpl")
public class IFSCDaoImpl implements IIFSCDao{

    @Autowired
    private SessionFactory sessionFactory;


    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BankInformation> fetchBankList() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BankInformation.class);
        return (List<BankInformation>) criteria.list();
//        Query query = sessionFactory.getCurrentSession().createQuery("from BankInformation where enabled = '1'");
//        return (List<BankInformation>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<StateInformation> fetchStateListBasedOnBank(Integer bankId) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT si.id, si.name FROM StateInformation si,BankStateDistrictCityIFSC bsdci WHERE si.id = bsdci.sid AND bsdci.bid =:bid");
        query.setParameter("bid", bankId);
        return (List<StateInformation>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<DistrictInformation> fetchDistrictListBasedOnBankState(Integer bankId, Integer stateId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM DistrictInformation di,BankStateDistrictCityIFSC bsdci WHERE di.id = bsdci.did AND bsdci.bid =:bid AND bsdci.sid =:sid");
        query.setParameter("bid", bankId);
        query.setParameter("sid", stateId);
        return (List<DistrictInformation>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<CityInformation> fetchCityListBasedOnBankStateDistrict(Integer bankId, Integer stateId, Integer districtId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM CityInformation ci,BankStateDistrictCityIFSC bsdci WHERE ci.id = bsdci.cid AND bsdci.bid =:bid AND bsdci.sid =:sid AND bsdci.did =:did");
        query.setParameter("bid", bankId);
        query.setParameter("sid", stateId);
        query.setParameter("did",districtId);
        return (List<CityInformation>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public BranchInformation fetchBranchBasedOnBankStateDistrictCity(Integer bankId, Integer stateId, Integer districtId, Integer cityId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM BranchInformation bi,BankStateDistrictCityIFSC bsdci WHERE bi.ifsc = bsdci.ifsc AND bsdci.bid =:bid AND bsdci.sid =:sid AND bsdci.did =:did AND bsdci.cid =:cid");
        query.setParameter("bid", bankId);
        query.setParameter("sid", stateId);
        query.setParameter("did",districtId);
        query.setParameter("cid",cityId);
        return (BranchInformation) query.uniqueResult();
    }
}
