package com.cfs.core.dao.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.entity.BankInformation;
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
}
