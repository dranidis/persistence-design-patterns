package com.asdt.persistence;

import java.util.*;

public class PersistenceFacade {
    private Map<Class<?>, IMapper> mappers = MapperFactory.getInstance().getAllMappers();

    // SINGLETON pattern
    static private PersistenceFacade instance = null;

    private PersistenceFacade() {
    }

    static public PersistenceFacade getInstance() {
        if (instance == null)
            instance = new PersistenceFacade();
        return instance;
    }
    // end of SINGLETON pattern

    public Object get(OID oid, Class<?> objectClass) {
        IMapper mapper = (IMapper) mappers.get(objectClass);
        if (mapper == null) {
            System.out.println("PFW Error: No Mapper added for " + objectClass);
            System.exit(1);
        }
        return mapper.get(oid);
    }

    public void put(OID oid, Object object) {
        IMapper mapper = (IMapper) mappers.get(object.getClass());
        if (mapper == null) {
            System.out.println("PFW Error: No Mapper added for " + object.getClass());
            System.exit(1);
        }
        mapper.put(oid, object);
    }
}
