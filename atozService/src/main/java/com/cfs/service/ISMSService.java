package com.cfs.service;

import com.cfs.core.entity.Users;
import com.cfs.core.objects.*;
import org.apache.commons.fileupload.FileItemStream;

/**
 * @author chopra
 * 05/12/17
 */
public interface ISMSService {
    boolean isUniqueUsername(String username);
    boolean userSignUp(UserDTO users);

    LoginResponse userLogin(LoginDTO loginDTO);
    LogoutResponse userLogout(String token);

    ServiceResponse createNewService(ServiceDTO serviceDTO);
    ServiceResponse fetchAllActiveServices(String token);
    boolean addMobileNumberAndMessage(String token, FileItemStream item,String message);
}
