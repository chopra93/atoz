package com.cfs.service.impl;

import com.cfs.core.enums.CompoundingEnum;
import com.cfs.core.enums.YearMonth;
import com.cfs.service.IEMIService;
import org.springframework.stereotype.Service;

/**
 * Created by Chopra on 15/11/17.
 */
@Service(value = "EMIServiceImpl")
public class EMIServiceImpl implements IEMIService {
    @Override
    public Long calculateEMI(Double principle, Double rate, Integer t, YearMonth yearMonth) {

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
        return Math.round(emiValue);
    }

    @Override
    public Long calculateFDInterest(Double principle, Double rate, Integer t, YearMonth yearMonth, CompoundingEnum compounding){
        /*
                A = P(1 + rt)   no compounding
                A = P × (1 + r/n)^nt periodic compounding
                A = P × e^rt continuous compounding

        */
        Double finalValue;
        Double time;

        if (YearMonth.YEAR == yearMonth){
            time = (double) t;
        }
        else{
            time = (double)t/12;
        }

        if (CompoundingEnum.C_N == compounding){
            rate = rate/100;
            finalValue = principle*(1+(rate*time));
        }
        else
        if (CompoundingEnum.C_C == compounding){
            finalValue = principle * Math.exp(rate*time);
        }
        else {
            int n;
            rate = rate/100;
            n = getValueInYear(compounding);
            finalValue = principle*(Math.pow((1+(rate/n)),(n*time)));
        }
        return Math.round(finalValue);
    }

    @Override
    public Long calculateRDInterest(Double monthlyPrinciple, Double rate, Integer t, YearMonth yearMonth, CompoundingEnum compounding){
        Double finalValue;
        Double val;
        Double numerator;
        Double denominator;
        Double time;
        int n;

        if (YearMonth.YEAR == yearMonth){
            time = (double) t;
        }
        else{
            time = (double)t/12;
        }
        rate = rate/100;
        n = getValueInYear(compounding);

        val = 1+(rate/n);
        numerator = Math.pow(val,(n*time))-1;
        denominator = 1- Math.pow(val,((double)-n/12));
        finalValue = monthlyPrinciple * (numerator/denominator);
        return Math.round(finalValue);
    }

    @Override
     public Integer getValueInYear(CompoundingEnum compounding){
        int n;
        if (CompoundingEnum.C_D == compounding){
            n = 365;
        }
        else
        if (CompoundingEnum.C_M == compounding){
            n = 12;
        }
        else
        if (CompoundingEnum.C_Q == compounding){
            n = 4;
        }
        else
        if (CompoundingEnum.C_S == compounding){
            n = 2;
        }
        else
        {
            n = 1;
        }
        return n;
    }


}
