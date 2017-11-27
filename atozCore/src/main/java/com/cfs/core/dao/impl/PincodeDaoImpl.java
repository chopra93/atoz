package com.cfs.core.dao.impl;

import com.cfs.core.dao.IPincodeDao;
import com.cfs.core.objects.PincodeInforamtionDTO;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chopra on 26/11/17.
 */
@Repository(value = "pincodeDaoImpl")
public class PincodeDaoImpl implements IPincodeDao {

    private static final Logger LOG = Logger.getLogger(PincodeDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public PincodeInforamtionDTO getPincodeInformationBasedOnPicode(String pincode) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT pi.state, pi.district, pi.city FROM PincodeInformation pi WHERE pi.pincode =:pincode");
        query.setParameter("pincode",pincode);
        List<Object[]> objList = (List<Object[]>)query.list();
        PincodeInforamtionDTO pincodeInforamtionDTO = new PincodeInforamtionDTO();

        for(Object[] obj:objList){
            pincodeInforamtionDTO.setState((String)obj[0]);
            pincodeInforamtionDTO.setCity((String)obj[2]);
            break;
        }
        return pincodeInforamtionDTO;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<String> getPincodeBasedOnStateAndCity(String state, String city){
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT pi.pincode FROM PincodeInformation pi WHERE pi.state =:state AND pi.city =:city" );
        query.setParameter("state",state);
        query.setParameter("city",city);
        List<Object[]> objList = (List<Object[]>)query.list();

        List<String> pincodeList = new ArrayList<>();
        for(Object[] obj:objList ){
            String pincode = (String) obj[0];
            pincodeList.add(pincode);
        }
        return pincodeList;
    }
}
