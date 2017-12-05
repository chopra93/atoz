package com.cfs.core.dao.impl;

import com.cfs.core.dao.ISMSDao;
import com.cfs.core.entity.Users;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chopra
 * 05/12/17
 */
@Repository(value = "smsDaoImpl")
public class SMSDaoImpl implements ISMSDao {

    private static final Logger LOG = Logger.getLogger(SMSDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean isUniqueUsername(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT ui.id FROM Users u WHERE u.username =:username");
        query.setParameter("username",username);
        Object obj = query.uniqueResult();
        if (obj == null)
            return false;
        else {
            return true;
        }
    }

    @Override
    public boolean userSignUp(Users user) {
        sessionFactory.getCurrentSession().save(user);
        return true;
    }
}
