package com.asdt.persistence;

import java.util.Map;

import com.asdt.util.Console;

public class PersistenceFacade {
    private Map<Class<?>, IMapper> mappers = MapperFactory.getInstance().getAllMappers();

    // SINGLETON pattern
    private static PersistenceFacade instance = null;

    private PersistenceFacade() {
    }

    public static PersistenceFacade getInstance() {
        if (instance == null)
            instance = new PersistenceFacade();
        return instance;
    }
    // end of SINGLETON pattern

    public Object get(OID oid, Class<?> objectClass) {
        IMapper mapper = mappers.get(objectClass);
        if (mapper == null) {
            Console.println("PFW Error: No Mapper added for " + objectClass);
            System.exit(1);
        }
        return mapper.get(oid);
    }

    public void put(OID oid, Object object) {
        IMapper mapper = mappers.get(object.getClass());
        if (mapper == null) {
            Console.println("PFW Error: No Mapper added for " + object.getClass());
            System.exit(1);
        }
        mapper.put(oid, object);
    }
}
