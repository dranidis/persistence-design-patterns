package com.asdt.persistence;

import java.util.*;

import com.asdt.domain.*;

// this should be removed when DATA DRIVEN
import com.asdt.post_persistence.*;

public class MapperFactory {
    // SINGLETON
    static private MapperFactory instance = new MapperFactory();

    private MapperFactory() {
    }

    static public MapperFactory getInstance() {
        return instance;
    }
    // END SINGLETON

    public Map<Class<?>, IMapper> getAllMappers() {
        Map<Class<?>, IMapper> mappers = new HashMap<>();
        // THESE SHOULD BE DATA DRIVEN
        mappers.put(Product.class, new ProductRDBMapper());
        mappers.put(Customer.class, new CustomerRDBMapper());
        mappers.put(OrderLine.class, new OrderLineRDBMapper());
        mappers.put(Manufacturer.class, new ManufacturerRDBMapper());
        return mappers;
    }
}
