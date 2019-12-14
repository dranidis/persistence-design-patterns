package com.asdt.persistence;

import java.sql.*;

abstract public class AbstractRDBMapper extends AbstractPersistenceMapper {
    private PreparedStatement sqlGetRecord;
    protected Connection connection;

    public AbstractRDBMapper(String tableName) {
        String url = "jdbc:derby:memory:demo";

        try {
            connection = DriverManager.getConnection(url);

            connection.setAutoCommit(false);

            sqlGetRecord = connection.prepareStatement("SELECT * from  " + tableName + " WHERE OID = ? ");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected Object getObjectFromStorage(OID oid) {
        String key = oid.toString();
        ResultSet resultSet = null;
        Object object = null;
        try {
            sqlGetRecord.setString(1, key);
            resultSet = sqlGetRecord.executeQuery();

            if (resultSet.next())
                object = getObjectFromRecord(oid, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return object;
    }

    abstract protected Object getObjectFromRecord(OID oid, ResultSet record) throws SQLException;

    protected void putObjectInStorage(OID oid, Object object) {
        System.out.println("Checking if object " + oid + " exists in Storage...");
        Object obj = PersistenceFacade.getInstance().get(oid, object.getClass());
        int result = 0;
        PreparedStatement preparedStatement;
        try {
            if (obj == null) {
                System.out.println("will INSERT");
                preparedStatement = getInsertStatement(oid, object);
            } else {
                System.out.println("will UPDATE");
                preparedStatement = getUpdateStatement(oid, object);
            }
            result = preparedStatement.executeUpdate();
            System.out.println("Statement Result : " + result);
            if (result == 0) {
                System.out.println("Unable to execute STATEMENT");
                connection.rollback();
            } else
                connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    abstract protected PreparedStatement getInsertStatement(OID oid, Object object) throws SQLException;

    abstract protected PreparedStatement getUpdateStatement(OID oid, Object object) throws SQLException;
}
