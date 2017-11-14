package com.cfs.service.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.entity.BankInformation;
import com.cfs.service.IIFSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chopra
 * 12/11/17
 */
@Service(value = "ifscServiceImpl")
public class IFSCServiceImpl implements IIFSCService{
    @Autowired
    private IIFSCDao ifscDao;

    @Override
    @Transactional
    public List<BankInformation> fetchBankDetails() {
        return ifscDao.fetchBankList();
    }
}
