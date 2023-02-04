package com.asdt.post_persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.asdt.domain.ManufacturerProxy;
import com.asdt.domain.Product;
import com.asdt.persistence.AbstractRDBMapper;
import com.asdt.persistence.OID;

public class ProductRDBMapper extends AbstractRDBMapper {
    public ProductRDBMapper() {
        super("PRODUCT");
    }

    protected Object getObjectFromRecord(OID oid, ResultSet resultSet) throws SQLException {
        Product product;
        String manufacturerOIDString = null;
        product = new Product();
        product.setOID(oid);
        product.setDescription(resultSet.getString("DESCRIPTION"));
        product.setPrice(resultSet.getInt("PRICE"));

        manufacturerOIDString = resultSet.getString("MANUFACTURER_OID");
        OID manuOID = new OID(manufacturerOIDString);
        // LAZY MATERIALIZATION using Virtual Proxy object
        product.setManufacturer(new ManufacturerProxy(manuOID));

        return product;
    }

    protected PreparedStatement getInsertStatement(OID oid, Object object) throws SQLException {
        System.out.println("NOT IMPLEMENTED");
        System.exit(1);
        return null;
    }

    protected PreparedStatement getUpdateStatement(OID oid, Object object) throws SQLException {
        System.out.println("NOT IMPLEMENTED");
        System.exit(1);
        return null;
    }
}
