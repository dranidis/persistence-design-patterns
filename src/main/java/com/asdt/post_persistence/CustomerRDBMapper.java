package com.asdt.post_persistence;

import java.sql.*;
import com.asdt.domain.*;
import com.asdt.persistence.*;

public class CustomerRDBMapper extends AbstractRDBMapper {
    private PreparedStatement sqlInsert;
    private PreparedStatement sqlUpdate;

    public CustomerRDBMapper() {
        super("CUSTOMER");
        try {
            sqlInsert = connection
                    .prepareStatement("INSERT INTO CUSTOMER ( OID, NAME, AGE ) " + "VALUES ( ? , ? , ? )");
            sqlUpdate = connection.prepareStatement("UPDATE CUSTOMER SET NAME = ?, AGE = ? WHERE OID = ?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected Object getObjectFromRecord(OID oid, ResultSet resultSet) throws SQLException {
        Customer customer;
        customer = new Customer();
        customer.setOID(oid);
        customer.setName(resultSet.getString("NAME"));
        customer.setAge(resultSet.getInt("AGE"));
        return customer;
    }

    protected PreparedStatement getInsertStatement(OID oid, Object object) throws SQLException {
        Customer customer = (Customer) object;
        sqlInsert.setString(1, oid.toString());
        sqlInsert.setString(2, customer.getName());
        sqlInsert.setInt(3, customer.getAge());
        return sqlInsert;
    }

    protected PreparedStatement getUpdateStatement(OID oid, Object object) throws SQLException {
        Customer customer = (Customer) object;
        sqlUpdate.setString(1, customer.getName());
        sqlUpdate.setInt(2, customer.getAge());
        sqlUpdate.setString(3, oid.toString());
        return sqlUpdate;
    }
}
