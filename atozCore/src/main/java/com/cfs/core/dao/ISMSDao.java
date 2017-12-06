package com.cfs.core.dao;

import com.cfs.core.entity.AccessToken;
import com.cfs.core.entity.Users;
import com.cfs.core.objects.LoginDTO;
import com.cfs.core.objects.UserDTO;

/**
 * @author chopra
 * 05/12/17
 */
public interface ISMSDao {
    boolean isUniqueUsername(String username);
    boolean userSignUp(UserDTO user);
    Users userLogin(LoginDTO loginDTO);
    boolean insertAccessToken(AccessToken accessToken);
}
