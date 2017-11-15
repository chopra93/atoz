package com.cfs.service.impl;

import com.cfs.core.enums.YearMonth;
import com.cfs.service.IEMIService;
import org.springframework.stereotype.Service;

/**
 * Created by Chopra on 15/11/17.
 */
@Service(value = "EMIServiceImpl")
public class EMIServiceImpl implements IEMIService {
    @Override
    public Double calculateEMI(Double principle, Double rate, Integer t, YearMonth yearMonth) {

        // E = P×r×(1 + r)n/((1 + r)n - 1)
        double emiValue;
        double fraction;
        int n;

        if (YearMonth.YEAR == yearMonth){
            n = t * 12 ;
        }
        else{
            n = t;
        }
        rate = rate / 1200;
        fraction = (Math.pow((1+rate),n))/(Math.pow((1+rate),n)-1);
        emiValue = principle * rate * fraction;
        return emiValue;
    }
}
