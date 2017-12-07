package com.cfs.core.dao;

import com.cfs.core.entity.AccessToken;
import com.cfs.core.entity.Service;
import com.cfs.core.entity.Users;
import com.cfs.core.objects.LoginDTO;
import com.cfs.core.objects.ServiceDTO;
import com.cfs.core.objects.UserDTO;

import java.util.List;

/**
 * @author chopra
 * 05/12/17
 */
public interface ISMSDao {
    boolean isUniqueUsername(String username);
    boolean userSignUp(UserDTO user);
    Users userLogin(LoginDTO loginDTO);
    Users fetchUserUsingUsername(String username);
    Integer userLogout(String accessToken);
    boolean insertAccessToken(AccessToken accessToken);
    boolean insertService(Service service);
    List<ServiceDTO> fetchAllActiveService(String username);
    String fetchAccessTokenBasedOnUser(Users userDetail);
}
