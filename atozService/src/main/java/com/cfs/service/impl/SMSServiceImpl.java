package com.cfs.service.impl;

import com.cfs.core.dao.ISMSDao;
import com.cfs.core.entity.AccessToken;
import com.cfs.core.entity.Users;
import com.cfs.core.objects.LoginDTO;
import com.cfs.core.objects.LoginResponse;
import com.cfs.core.objects.UserDTO;
import com.cfs.service.ISMSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
    public boolean insertUser(UserDTO users) {
        if ((users.getPwd()).equals(users.getPwdCompare()))
        {
            return smsDao.userSignUp(users);
        }
        else
            return false;
    }

    @Override
    public LoginResponse userLogin(LoginDTO loginDTO){
        if (loginDTO == null || loginDTO.getUsername() == null || loginDTO.getPwd() == null){
            return null;
        }
        LoginResponse loginResponse = new LoginResponse();
        Users user = smsDao.userLogin(loginDTO);
        if(user == null){
            LOGGER.info("Invalid username and password");
            loginResponse.setMessage("Username and password does not match");
            loginResponse.setStatus(500);
            return loginResponse;
        }
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        Date date = new Date();
        long expiryTime = date.getTime() + 11352960000L;

        AccessToken accessToken = new AccessToken();
        accessToken.setToken(token);
        accessToken.setExpiry(expiryTime);
        accessToken.setAccessTokenUsers(user);
        accessToken.setActive(true);

        boolean accessTokenInsertResponse = smsDao.insertAccessToken(accessToken);
        if (accessTokenInsertResponse){
            loginResponse.setMessage("success");
            loginResponse.setToken(token);
            loginResponse.setStatus(200);
        }
        else {
            loginResponse.setMessage("failure");
            loginResponse.setStatus(500);
        }
        return loginResponse;
    }
}
