package com.cfs.service.impl;

import com.cfs.constant.AtozConstants;
import com.cfs.core.dao.ISMSDao;
import com.cfs.core.entity.*;
import com.cfs.core.enums.ServiceEnum;
import com.cfs.core.objects.*;
import com.cfs.service.IRedisService;
import com.cfs.service.ISMSService;
import com.google.gson.Gson;
import com.mongodb.util.JSON;
import com.opencsv.CSVReader;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
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
    private static final Gson GSON = new Gson();


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
            try {
                return smsDao.userSignUp(users);
            }
            catch (Exception e ){
                return false;
            }
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
            String valueJsonUsername = redisService.getFromMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,tokenFromDB);
            if (valueJsonUsername == null){
                redisService.putInMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,tokenFromDB, GSON.toJson(convertFromUserDTOTOUsers(user)));
            }
            else {
                UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
                if (!tokenFromDB.equals(userDetail.getAccessToken())){
                    redisService.putInMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,tokenFromDB, GSON.toJson(convertFromUserDTOTOUsers(user)));
                }
            }
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
            redisService.putInMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,token, GSON.toJson(user));
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
        String valueJsonUsername = redisService.getFromMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,serviceDTO.getToken());
        if (StringUtils.isEmpty(valueJsonUsername)){
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

            UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
            Users users = smsDao.fetchUserUsingUsername(userDetail.getUsername());
            if (userDetail == null){
                serviceResponse.setMessage("Token Expired");
                serviceResponse.setStatusCode(500);
                return serviceResponse;
            }
            serviceEntity.setServiceUsers(users);

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
        String valueJsonUsername = redisService.getFromMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,token);
        if (StringUtils.isEmpty(valueJsonUsername)){
            serviceResponse.setMessage("Invalid Token");
            serviceResponse.setStatusCode(500);
            return serviceResponse;
        }
        UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
        List<ServiceDTO> serviceDTOS = smsDao.fetchAllActiveService(userDetail.getUsername());
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

    private UserDTO convertFromUserDTOTOUsers(Users users){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(users.getName());
        userDTO.setUsername(users.getUsername());
        userDTO.setAccessToken(users.getAccessToken().getToken());
        userDTO.setEmail(users.getEmail());
        userDTO.setPhone(users.getPhone());
        return userDTO;
    }

    @Override
    public boolean addMobileNumberAndMessage(String token, FileItemStream item, String message) {
        try {
            Message messageObject = smsDao.getMessage(message);
            if (messageObject == null){
                LOGGER.info("null message respone received");
                Message insetToMessage = new Message();
                insetToMessage.setActive(true);
                insetToMessage.setMessage(message);
                boolean responseFromMessageInsertion = smsDao.addMessage(insetToMessage);
                if (!responseFromMessageInsertion){
                    return false;
                }
                messageObject = smsDao.getMessage(message);
            }

            String valueJsonUsername = redisService.getFromMap(AtozConstants.USERTOKEN_USERNAME_MAP_NAME,token);
            UserDTO userDetail = GSON.fromJson(valueJsonUsername, UserDTO.class);
            Users users = smsDao.fetchUserUsingUsername(userDetail.getUsername());

            int val = findRecordTable(users.getId());

            InputStreamReader inputStreamReader = new InputStreamReader(item.openStream());
            CSVReader reader = new CSVReader(inputStreamReader);
            String[] line;
            while ((line = reader.readNext()) != null) {
                int currentLenth = line.length;
                for (int i=0;i<currentLenth;i++){
                    LOGGER.info("size of numbers = "+line.length);
                    if (line[i].length() <13 && line[i].length()>4){

                        switch (val){
                            case 0:
                                RecordOne recordOne = new RecordOne();
                                recordOne.setActive(true);
                                recordOne.setMessage(messageObject);
                                recordOne.setMobile(line[i]);
                                recordOne.setRecordOneUser(users);

                                smsDao.addRecord(recordOne);
                                break;
                            case 1:
                                RecordTwo recordTwo = new RecordTwo();
                                recordTwo.setActive(true);
                                recordTwo.setMessage(messageObject);
                                recordTwo.setMobile(line[i]);
                                recordTwo.setRecordTwoUser(users);

                                smsDao.addRecordTwo(recordTwo);
                                break;
                            case 2:
                                RecordThree recordThree = new RecordThree();
                                recordThree.setActive(true);
                                recordThree.setMessage(messageObject);
                                recordThree.setMobile(line[i]);
                                recordThree.setRecordThreeUser(users);

                                smsDao.addRecordThree(recordThree);
                                break;
                            case 3:
                                RecordFour recordFour = new RecordFour();
                                recordFour.setActive(true);
                                recordFour.setMessage(messageObject);
                                recordFour.setMobile(line[i]);
                                recordFour.setRecordFourUser(users);

                                smsDao.addRecordFour(recordFour);
                                break;
                            case 4:
                                RecordFive recordFive = new RecordFive();
                                recordFive.setActive(true);
                                recordFive.setMessage(messageObject);
                                recordFive.setMobile(line[i]);
                                recordFive.setRecordFiveUser(users);

                                smsDao.addRecordFive(recordFive);
                                break;
                        }
                    }
                }
            }
            return true;

        } catch (IOException e) {
            LOGGER.info("Exception Occured");
            return false;
        }
    }

    private int findRecordTable(int id){
        return id%5;
    }

}
