package com.cfs.service.impl;

import com.cfs.core.dao.IIFSCDao;
import com.cfs.core.dao.IPincodeDao;
import com.cfs.core.objects.PincodeInforamtionDTO;
import com.cfs.service.IPincodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chopra on 26/11/17.
 */
@Service(value = "pincodeServiceImpl")
public class PincodeServiceImpl implements IPincodeService {

    private static final Logger LOG = Logger.getLogger(PincodeServiceImpl.class);

    @Autowired
    private IPincodeDao pincodeDao;

    @Override
    public PincodeInforamtionDTO getPincodeInformationBasedOnPincode(String pincode) {
        if (pincode == null){
            LOG.info("pincode cannot be null");
            return null;
        }
        return pincodeDao.getPincodeInformationBasedOnPicode(pincode);
    }

    @Override
    public List<String> getPincodeBasedOnStateAndCity(String state, String city) {
        if (state == null || city == null){
            LOG.info("state or city cannot be null");
            return null;
        }
        return pincodeDao.getPincodeBasedOnStateAndCity(state,city);
    }
}
