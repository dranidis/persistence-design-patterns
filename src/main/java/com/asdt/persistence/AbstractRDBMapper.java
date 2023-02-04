package com.asdt.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.asdt.util.Console;

public abstract class AbstractRDBMapper extends AbstractPersistenceMapper {
    private PreparedStatement sqlGetRecord;
    protected Connection connection;

    protected AbstractRDBMapper(String tableName) {
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

    protected abstract Object getObjectFromRecord(OID oid, ResultSet resultSet) throws SQLException;

    protected void putObjectInStorage(OID oid, Object object) {
        Console.println("Checking if object " + oid + " exists in Storage...");
        Object obj = PersistenceFacade.getInstance().get(oid, object.getClass());
        int result = 0;
        PreparedStatement preparedStatement;
        try {
            if (obj == null) {
                Console.println("will INSERT");
                preparedStatement = getInsertStatement(oid, object);
            } else {
                Console.println("will UPDATE");
                preparedStatement = getUpdateStatement(oid, object);
            }
            result = preparedStatement.executeUpdate();
            Console.println("Statement Result : " + result);
            if (result == 0) {
                Console.println("Unable to execute STATEMENT");
                connection.rollback();
            } else
                connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract PreparedStatement getInsertStatement(OID oid, Object object) throws SQLException;

    protected abstract PreparedStatement getUpdateStatement(OID oid, Object object) throws SQLException;
}
