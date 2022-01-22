package com.asdt.domain;

import com.asdt.persistence.OID;
import com.asdt.persistence.PersistenceFacade;

public class ManufacturerProxy implements IManufacturer {
    // VIRTUAL PROXY pattern
    private IManufacturer realSubject = null;

    private OID realSubjectOID;

    public ManufacturerProxy(OID oid) {
        realSubjectOID = oid;
    }

    private IManufacturer getRealSubject() {
        if (realSubject == null)
            realSubject = (IManufacturer) PersistenceFacade.getInstance().get(realSubjectOID, Manufacturer.class);
        return realSubject;
    }

    public String getAddress() {
        return getRealSubject().getAddress();
    }

    public String toString() {
        return "ManufacturerProxy: OID: [" + realSubjectOID + "] realSubject: [" + realSubject + "]";
    }    
}
