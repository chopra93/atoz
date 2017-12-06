package com.cfs.core.dao.impl;

import com.cfs.core.dao.ISMSDao;
import com.cfs.core.entity.AccessToken;
import com.cfs.core.entity.Users;
import com.cfs.core.objects.LoginDTO;
import com.cfs.core.objects.UserDTO;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

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
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u.id FROM Users u WHERE u.username =:username");
        query.setParameter("username", username);
        Object obj = query.uniqueResult();
        if (obj == null)
            return false;
        else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean userSignUp(UserDTO user) {
        Users userData = new Users();
        userData.setName(user.getName());
        userData.setUsername(user.getUsername());
        userData.setEmail(user.getEmail());
        userData.setPhone(user.getPhone());
        userData.setPwd(user.getPwd());
        try {
            sessionFactory.getCurrentSession().save(userData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Users userLogin(LoginDTO loginDTO) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Users u WHERE u.username =:username AND u.pwd=:pwd");
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("pwd", loginDTO.getPwd());
        Object obj = query.uniqueResult();
        if (obj == null)
            return null;
        else {
            Users user = (Users) obj;
            return user;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public boolean insertAccessToken(AccessToken accessToken) {
        try {
            sessionFactory.getCurrentSession().save(accessToken);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}