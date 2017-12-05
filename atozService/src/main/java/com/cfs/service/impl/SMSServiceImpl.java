package com.cfs.service.impl;

import com.cfs.core.dao.ISMSDao;
import com.cfs.core.entity.Users;
import com.cfs.service.ISMSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chopra
 * 05/12/17
 */
@Service
public class SMSServiceImpl implements ISMSService {

    private static final Logger LOGGER = Logger.getLogger(SMSServiceImpl.class);

    @Autowired
    private  ISMSDao smsDao;

    @Override
    public boolean isUniqueUsername(String username) {
        return smsDao.isUniqueUsername(username);
    }

    @Override
    public boolean insertUser(Users users) {
        return smsDao.userSignUp(users);
    }
}
