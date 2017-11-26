package com.cfs.core.dao;

import com.cfs.core.objects.PincodeInforamtionDTO;

import java.util.List;

/**
 * Created by Chopra on 26/11/17.
 */
public interface IPincodeDao {
    PincodeInforamtionDTO getPincodeInformationBasedOnPicode(String pincode);
    List<String> getPincodeBasedOnStateAndCity(String state, String city);
}
