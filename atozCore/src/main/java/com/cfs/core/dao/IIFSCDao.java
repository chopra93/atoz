package com.cfs.core.dao;

import com.cfs.core.entity.BankInformation;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
public interface IIFSCDao {
    List<BankInformation> fetchBankList();
}
