package com.cfs.service.impl;

import com.cfs.constant.AtozConstants;
import com.cfs.core.dao.ISMSDao;
import com.cfs.core.entity.AccessToken;
import com.cfs.core.entity.Users;
import com.cfs.core.enums.ServiceEnum;
import com.cfs.core.objects.*;
import com.cfs.service.IRedisService;
import com.cfs.service.ISMSService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private IRedisService redisService;

    @Override
    public boolean isUniqueUsername(String username) {
        return smsDao.isUniqueUsername(username);
    }

    @Override
    public boolean userSignUp(UserDTO users) {
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
        String tokenFromDB = smsDao.fetchAccessTokenBasedOnUser(user);
        if (tokenFromDB!= null ){
            loginResponse.setMessage("success");
            loginResponse.setToken(tokenFromDB);
            loginResponse.setStatus(200);
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
            redisService.putInMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,token,user.getUsername());
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

    @Override
    public LogoutResponse userLogout(String token){
        LogoutResponse logoutResponse = new LogoutResponse();
        Long val = redisService.deleteFromMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,token);
        if (val == 0){
            logoutResponse.setMessage("failure");
            logoutResponse.setStatusCode(500);
        }
        else {
            int tokenDeleteResponse = smsDao.userLogout(token);
            if (tokenDeleteResponse !=0){
                logoutResponse.setMessage("success");
                logoutResponse.setStatusCode(200);
            }
            else {
                logoutResponse.setMessage("failure");
                logoutResponse.setStatusCode(200);
            }
        }
        return logoutResponse;
    }

    @Override
    public ServiceResponse createNewService(ServiceDTO serviceDTO){
        ServiceResponse serviceResponse = new ServiceResponse();
        if (serviceDTO == null || serviceDTO.getToken() == null){
            serviceResponse.setMessage("Object cannot be null");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        String username = redisService.getFromMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,serviceDTO.getToken());
        if (StringUtils.isEmpty(username)){
            serviceResponse.setMessage("Invalid Token");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        else {
            com.cfs.core.entity.Service serviceEntity = new com.cfs.core.entity.Service();
            Date date = new Date();
            long expiryTime = date.getTime() + 11352960000L;

            serviceEntity.setExpiry(expiryTime);
            serviceEntity.setLimit(serviceDTO.getLimit());
            serviceEntity.setActive(true);
            if ((ServiceEnum.OTP.toString()).equals(serviceDTO.getServiceType())){
                serviceEntity.setServiceType(ServiceEnum.OTP.toString());
            }
            else {
                serviceEntity.setServiceType(ServiceEnum.BULK.toString());
            }

            Users userDetail = smsDao.fetchUserUsingUsername(username);
            if (userDetail == null){
                serviceResponse.setMessage("Token Expired");
                serviceResponse.setStatusCode(500);
                return serviceResponse;
            }
            serviceEntity.setServiceUsers(userDetail);

            try {
                Boolean serviceInsertionResponse = smsDao.insertService(serviceEntity);
                if (serviceInsertionResponse){
                    serviceResponse.setMessage("Success");
                    serviceResponse.setStatusCode(200);
                    return serviceResponse;
                }
                else {
                    serviceResponse.setMessage("Failure");
                    serviceResponse.setStatusCode(500);
                    return serviceResponse;
                }
            }
            catch (Exception e){
                serviceResponse.setMessage("Exception");
                serviceResponse.setStatusCode(500);
                return serviceResponse;
            }

        }
    }

    @Override
    public ServiceResponse fetchAllActiveServices(String token){
        ServiceResponse serviceResponse = new ServiceResponse();
        String username = redisService.getFromMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,token);
        if (StringUtils.isEmpty(username)){
            serviceResponse.setMessage("Invalid Token");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        List<ServiceDTO> serviceDTOS = smsDao.fetchAllActiveService(username);
        if (serviceDTOS.isEmpty()){
            serviceResponse.setMessage("No Active Service");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        serviceResponse.setMessage("Service List");
        serviceResponse.setStatusCode(200);
        serviceResponse.setServiceList(serviceDTOS);
        return serviceResponse;
    }

}
