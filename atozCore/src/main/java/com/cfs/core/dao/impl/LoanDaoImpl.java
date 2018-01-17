package com.cfs.core.dao.impl;

import com.cfs.core.dao.ILoanDao;
import com.cfs.core.entity.LoanInformation;
import com.cfs.core.entity.LoanUsers;
import com.cfs.core.enums.LoanEnum;
import com.cfs.core.objects.IFSC;
import com.cfs.core.objects.LoanRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chopra
 * 17/01/18
 */
@Repository(value = "loanDaoImpl")
public class LoanDaoImpl implements ILoanDao{
    private static final Logger LOG = Logger.getLogger(LoanDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public LoanUsers userExist(String phoneNumber) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT lu.id, lu.name, lu.email, lu.phone FROM LoanUsers lu WHERE lu.phone = :phone");
        query.setParameter("phone", phoneNumber);
        List<Object[]> loanUserObject = (List<Object[]>)query.list();
        if(loanUserObject == null || loanUserObject.isEmpty()){
            return null;
        }
        else{
            LoanUsers loanUsers = new LoanUsers();
            for (Object[] object: loanUserObject){
                Integer i = (Integer) object[0];
                String nm = (String)object[1];
                String em = (String)object[2];
                String ph = (String)object[3];

                loanUsers.setId(i);
                loanUsers.setName(nm);
                loanUsers.setEmail(em);
                loanUsers.setPhone(ph);
                break;
            }
            return loanUsers;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean insertLoanInformation(LoanRequest loanRequest, LoanUsers loanUsers){
        if (null == loanUsers){
            loanUsers = new LoanUsers();
            loanUsers.setName(loanRequest.getName());
            loanUsers.setEmail(loanRequest.getEmail());
            loanUsers.setPhone(loanRequest.getPhone());

            try {
                saveLoanUser(loanUsers);
                loanUsers = userExist(loanUsers.getPhone());
            }
            catch (Exception e){
                LOG.info("Exception occured while saving loan and user information");
                return false;
            }
        }

        LoanInformation loanInformation = new LoanInformation();
        loanInformation.setLoanAmount(loanRequest.getLoanAmount());
        loanInformation.setPreferenceOne(loanRequest.getPreferenceOne());
        loanInformation.setPreferenceTwo(loanRequest.getPreferenceTwo());
        loanInformation.setPreferenceThree(loanRequest.getPreferenceThree());
        loanInformation.setActive(true);
        loanInformation.setType(loanRequest.getType().toString());
        loanInformation.setLoanUsers(loanUsers);

        try {
            saveLoanInformation(loanInformation);
            return true;

        }
        catch (Exception e){
            LOG.info("Exception occured while saving loan and user information");
            return false;
        }
    }

    private boolean saveLoanUser(LoanUsers loanUsers){
        sessionFactory.getCurrentSession().save(loanUsers);
        return true;
    }

    private boolean saveLoanInformation(LoanInformation loanInformation){
        sessionFactory.getCurrentSession().save(loanInformation);
        return true;
    }

}
