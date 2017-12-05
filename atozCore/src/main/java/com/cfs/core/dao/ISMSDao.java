package com.cfs.core.dao;

import com.cfs.core.entity.Users;

/**
 * @author chopra
 * 05/12/17
 */
public interface ISMSDao {
    boolean isUniqueUsername(String username);
    boolean userSignUp(Users user);
}
