package com.asdt.domain;

import com.asdt.persistence.OID;
import com.asdt.persistence.PersistenceFacade;

/**
 * This implementation of the pattern removes the common interface and the Proxy
 * class extends the Subject class and wraps the Subject object.
 */
public class ManufacturerProxy extends Manufacturer {
    // VIRTUAL PROXY pattern
    private Manufacturer realSubject = null;

    private OID realSubjectOID;

    public ManufacturerProxy(OID oid) {
        realSubjectOID = oid;
    }

    private Manufacturer getRealSubject() {
        if (realSubject == null)
            realSubject = (Manufacturer) PersistenceFacade.getInstance().get(realSubjectOID, Manufacturer.class);
        return realSubject;
    }

    public String getAddress() {
        return getRealSubject().getAddress();
    }

    public String toString() {
        return "ManufacturerProxyExtension: OID: [" + realSubjectOID + "] realSubject: [" + realSubject + "]";
    }
}
