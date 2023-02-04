package com.asdt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.asdt.domain.Customer;
import com.asdt.domain.OrderLine;
import com.asdt.domain.Product;
import com.asdt.persistence.OID;
import com.asdt.persistence.PersistenceFacade;

public class Main {
    public static void main(String args[]) {
        createDB();

        PersistenceFacade persistenceFacade = PersistenceFacade.getInstance();

        Customer customer;

        System.out.println("EXEC: customer = (Customer) persistenceFacade.get( new OID( \"c123\" ), Customer.class );");

        customer = (Customer) persistenceFacade.get(new OID("c123"), Customer.class);

        System.out.println(customer);

        System.out.println();
        pause();

        // reading the same product from the database causes the
        // object to be read from the cache instead of the db itself.

        System.out.println("EXEC: customer = (Customer) persistenceFacade.get( new OID( \"c123\" ), Customer.class );");

        customer = (Customer) persistenceFacade.get(new OID("c123"), Customer.class);
        System.out.println(customer);

        System.out.println();
        pause();

        // reading a Product from the database ...
        Product product;

        System.out.println("EXEC: product = (Product) persistenceFacade.get( new OID( \"p123\" ), Product.class );");
        product = (Product) persistenceFacade.get(new OID("p123"), Product.class);

        System.out.println(product);
        System.out.println();

        System.out.println();
        pause();

        // reading the same product from the database causes the
        // object to be read from the cache instead of the db itself.

        System.out.println("EXEC: product = (Product) persistenceFacade.get( new OID( \"p123\" ), Product.class );");
        product = (Product) persistenceFacade.get(new OID("p123"), Product.class);

        System.out.println(product);

        System.out.println();
        pause();

        // Now trying to do smt more complicated.
        // Orderline is an object which aggregates a product
        // When we read an orderline from the database a product is also read from
        // the db at the same time.
        OrderLine orderLine;

        System.out.println(
                "EXEC: orderLine = (OrderLine) persistenceFacade.get( new OID( \"o999\" ), OrderLine.class );");
        orderLine = (OrderLine) persistenceFacade.get(new OID("o999"), OrderLine.class);
        System.out.println(orderLine);

        System.out.println();
        pause();

        // Although product also aggregates a Manufacturer the manufacturer object
        // is not read from the db...

        System.out.println("EXEC: product = (Product) persistenceFacade.get( new OID( \"p123\" ), Product.class );");

        product = (Product) persistenceFacade.get(new OID("p123"), Product.class);
        System.out.println();
        pause();

        // ... until we actucaly need it (Proxy)
        System.out.println("EXEC: System.out.println( product.getManufacturerAddress());");

        System.out.println(product.getManufacturerAddress());

        System.out.println();
        System.out.println(product);
        pause();

        /*
         *
         * Customer customer1 = new Customer(); customer1.setOID(new OID("249a"));
         * customer1.setName("Test name"); customer1.setAge(28); persistenceFacade.put (
         * new OID( "249a" ), customer1 ); System.out.println(); customer = (Customer)
         * persistenceFacade.get( new OID( "249a" ), Customer.class );
         * System.out.println(customer);
         *
         * System.out.println(); pause();
         *
         * customer1.setName("Other name");
         *
         * persistenceFacade.put ( customer1.getOID() , customer1 ); customer =
         * (Customer) persistenceFacade.get( new OID( "249a" ), Customer.class );
         * System.out.println(customer); pause();
         *
         * System.out.println("WRITING AN ORDER AFTER CHANGING QTY");
         *
         * orderLine.setQuantity(23); persistenceFacade.put ( orderLine.getOID() ,
         * orderLine );
         *
         * System.out.println("READING THE CHANGED ORDER");
         *
         * orderLine = (OrderLine) persistenceFacade.get( new OID( "o999" ),
         * OrderLine.class ); System.out.println(orderLine); OrderLine orderLine1 = new
         * OrderLine(); orderLine1.setOID(new OID( "949a" ));
         * orderLine1.setProduct(product); orderLine1.setQuantity(111);
         *
         * persistenceFacade.put ( orderLine1.getOID() , orderLine1 );
         */

        shutDownDB();
    }

    private static void pause() {
        // JOptionPane.showMessageDialog(null, "");

        System.out.print("Press any key to continue . . . ");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDB() {
        Connection conn;

        String dbUrl = "jdbc:derby:memory:demo;create=true";

        try {
            conn = DriverManager.getConnection(dbUrl);

            Statement stmt = conn.createStatement();

            // drop table
            // stmt.executeUpdate("Drop Table ...");
            stmt.executeUpdate(
                    "create table PRODUCT(OID varchar(5), DESCRIPTION varchar(40), PRICE int, MANUFACTURER_OID varchar(5))");
            stmt.executeUpdate("insert into PRODUCT values ('p123','Coffee',3,'m001')");
            stmt.executeUpdate("insert into PRODUCT values ('p101','Beer',3,'m001')");

            stmt.executeUpdate("create table CUSTOMER(OID varchar(5), NAME varchar(40), AGE int)");
            stmt.executeUpdate("insert into CUSTOMER values ('c123','George',24)");

            stmt.executeUpdate("create table ORDERLINE(OID varchar(5), PRODUCT_OID varchar(5), QUANTITY int)");
            stmt.executeUpdate("insert into ORDERLINE values ( 'o999', 'p101', 3 )");

            stmt.executeUpdate("create table MANUFACTURER(OID varchar(5), ADDRESS varchar(40))");
            stmt.executeUpdate("insert into MANUFACTURER values ( 'm001', 'Dodekanisou 35')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void shutDownDB() {
        boolean gotSQLExc = false;
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            if (se.getSQLState().equals("XJ015")) {
                gotSQLExc = true;
            }
        }
        if (!gotSQLExc) {
            System.out.println("Database did not shut down normally");
        } else {
            System.out.println("Database shut down normally");
        }

    }
}
