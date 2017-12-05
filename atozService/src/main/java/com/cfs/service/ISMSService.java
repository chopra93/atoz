package com.cfs.service;

import com.cfs.core.entity.Users;

/**
 * @author chopra
 * 05/12/17
 */
public interface ISMSService {
    boolean isUniqueUsername(String username);
    boolean insertUser(Users users);
}
