package com.asdt.persistence;

public interface IMapper {
    Object get(OID oid);

    void put(OID oid, Object object);
}
