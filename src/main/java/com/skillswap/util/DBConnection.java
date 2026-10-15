package com.skillswap.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {

        try {
            Class.forName(DBConstants.DB_DRIVER);

            Connection connection = DriverManager.getConnection(
                    DBConstants.DB_URL,
                    DBConstants.DB_USER,
                    DBConstants.DB_PASSWORD
            );

            System.out.println("Database connected successfully!");

            return connection;

        } catch (ClassNotFoundException e) {

            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();

        } catch (SQLException e) {

            System.err.println("Database connection failed!");
            e.printStackTrace();
        }

        return null;
    }
}