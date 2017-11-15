package com.cfs.service;

import com.cfs.core.enums.YearMonth;

/**
 * Created by Chopra on 15/11/17.
 */
public interface IEMIService {
    Double calculateEMI(Double principle, Double rate, Integer t, YearMonth yearMonth);
}
