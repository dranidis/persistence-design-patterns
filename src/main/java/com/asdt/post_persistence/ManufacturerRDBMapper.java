package com.asdt.post_persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.asdt.domain.Manufacturer;
import com.asdt.persistence.AbstractRDBMapper;
import com.asdt.persistence.OID;
import com.asdt.util.Console;


public class ManufacturerRDBMapper extends AbstractRDBMapper {
    public ManufacturerRDBMapper() {
        super("MANUFACTURER");
    }

    protected Object getObjectFromRecord(OID oid, ResultSet resultSet) {
        Manufacturer manufacturer;
        manufacturer = new Manufacturer();
        manufacturer.setOID(oid);
        try {
            manufacturer.setAddress(resultSet.getString("ADDRESS"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return manufacturer;
    }

    protected PreparedStatement getUpdateStatement(OID oid, Object object) throws SQLException {
        Console.println("NOT IMPLEMENTED");
        System.exit(1);
        return null;
    }

    protected PreparedStatement getInsertStatement(OID oid, Object object) throws SQLException {
        Console.println("NOT IMPLEMENTED");
        System.exit(1);
        return null;
    }
}
