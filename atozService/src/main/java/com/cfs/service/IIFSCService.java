package com.cfs.service;

import com.cfs.core.entity.*;
import com.cfs.core.objects.Information;

import java.util.List;
import java.util.Set;

/**
 * @author chopra
 * 12/11/17
 */
public interface IIFSCService {
    Set<Information> BankDetails(Integer bankId, Integer stateId, Integer districtId);
    BranchInformation BankDetails(Integer bankId, Integer stateId, Integer districtId, Integer cityId);
}
