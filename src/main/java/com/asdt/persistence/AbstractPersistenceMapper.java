package com.asdt.persistence;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPersistenceMapper implements IMapper {
    private final Map<String, Object> cashedObjects = new HashMap<>();

    // TEMPLATE METHOD pattern
    public final synchronized Object get(final OID oid) {
        Object object = cashedObjects.get(oid.toString());
        if (object == null) {
            System.out.println("Reading object " + oid + " from Storage");
            object = getObjectFromStorage(oid);
            if (object != null) {
                cashedObjects.put(oid.toString(), object);
                System.out.println("Retrieved object " + oid + " from Storage");
            } else
                System.out.println("NOT FOUND object " + oid + " in Storage");
        } else {
            System.out.println("Retrieving object " + oid + " from Cache");
        }
        return object;
    }

    protected abstract Object getObjectFromStorage(OID oid);
    // END TEMPLATE

    public final synchronized void put(final OID oid, final Object object) {
        putObjectInStorage(oid, object);
        cashedObjects.put(oid.toString(), object);
    }

    protected abstract void putObjectInStorage(OID oid, Object object);
}
