package com.asdt.domain;

import com.asdt.persistence.PersistentObject;

public class Manufacturer extends PersistentObject implements IManufacturer {
    private String address;

    public void setAddress(String a) {
        address = a;
    }

    public String getAddress() {
        return address;
    }
}
