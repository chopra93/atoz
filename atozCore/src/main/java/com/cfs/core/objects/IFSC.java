package com.cfs.core.objects;

import java.util.List;
import java.util.Set;

/**
 * Created by Chopra on 16/11/17.
 */
public class IFSC {
    private Set<Information> ifscInformation;

    public Set<Information> getIfscInformation() {
        return ifscInformation;
    }

    public void setIfscInformation(Set<Information> ifscInformation) {
        this.ifscInformation = ifscInformation;
    }

    @Override
    public String toString() {
        return "IFSC{" +
                "ifscInformation=" + ifscInformation +
                '}';
    }

}
