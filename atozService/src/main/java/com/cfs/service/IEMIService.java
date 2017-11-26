package com.cfs.service;

import com.cfs.core.enums.CompoundingEnum;
import com.cfs.core.enums.YearMonth;

/**
 * Created by Chopra on 15/11/17.
 */
public interface IEMIService {
    Long calculateEMI(Double principle, Double rate, Integer t, YearMonth yearMonth);
    Long calculateFDInterest(Double principle, Double rate, Integer t, YearMonth yearMonth, CompoundingEnum compounding);
    Long calculateRDInterest(Double monthlyPrinciple, Double rate, Integer t, YearMonth yearMonth, CompoundingEnum compounding);
    Integer getValueInYear(CompoundingEnum compounding);
}
