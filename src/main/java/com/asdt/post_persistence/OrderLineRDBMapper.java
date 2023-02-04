package com.asdt.post_persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.asdt.domain.OrderLine;
import com.asdt.domain.Product;
import com.asdt.persistence.AbstractRDBMapper;
import com.asdt.persistence.OID;
import com.asdt.persistence.PersistenceFacade;


public class OrderLineRDBMapper extends AbstractRDBMapper {
    private PreparedStatement sqlInsert;
    private PreparedStatement sqlUpdate;

    public OrderLineRDBMapper() {
        super("ORDERLINE");
        try {
            sqlInsert = connection
                    .prepareStatement("INSERT INTO ORDERLINE ( OID, PRODUCT_OID, QUANTITY ) " + "VALUES ( ? , ? , ? )");
            sqlUpdate = connection.prepareStatement("UPDATE ORDERLINE SET PRODUCT_OID = ?, QUANTITY = ? WHERE OID = ?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected Object getObjectFromRecord(OID oid, ResultSet resultSet) throws SQLException {
        OrderLine orderLine;
        orderLine = new OrderLine();
        orderLine.setOID(oid);
        OID productOID = new OID(resultSet.getString("PRODUCT_OID"));
        // EAGER MATERIALIZATION of Reference object
        Product product = (Product) PersistenceFacade.getInstance().get(productOID, Product.class);
        orderLine.setProduct(product);
        orderLine.setQuantity(resultSet.getInt("QUANTITY"));
        return orderLine;
    }

    protected PreparedStatement getInsertStatement(OID oid, Object object) throws SQLException {
        OrderLine orderLine = (OrderLine) object;
        sqlInsert.setString(1, oid.toString());
        sqlInsert.setString(2, orderLine.getProduct().getOID().toString());
        sqlInsert.setInt(3, orderLine.getQuantity());
        return sqlInsert;
    }

    protected PreparedStatement getUpdateStatement(OID oid, Object object) throws SQLException {
        OrderLine orderLine = (OrderLine) object;
        sqlUpdate.setString(1, orderLine.getProduct().getOID().toString());
        sqlUpdate.setInt(2, orderLine.getQuantity());
        sqlUpdate.setString(3, oid.toString());
        return sqlUpdate;
    }
}
