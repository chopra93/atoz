package com.cfs.service;

import com.cfs.core.entity.Users;
import com.cfs.core.objects.LoginDTO;
import com.cfs.core.objects.LoginResponse;
import com.cfs.core.objects.UserDTO;

/**
 * @author chopra
 * 05/12/17
 */
public interface ISMSService {
    boolean isUniqueUsername(String username);
    boolean insertUser(UserDTO users);
    LoginResponse userLogin(LoginDTO loginDTO);
}
