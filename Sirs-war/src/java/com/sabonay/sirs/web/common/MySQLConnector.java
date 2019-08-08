/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author seth
 */
public class MySQLConnector {

    Connection connection = null;

    public MySQLConnector() {
        makeConnection();
    }

    public void makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(ApplicationConstant.CONNECTION_URL, ApplicationConstant.DATABASE_USER, ApplicationConstant.DATABASE_PASSWORD);
        } catch (Exception e) {
            System.out.println("Unable to Establish conection : "+e);
        }
    }

    public ResultSet processQuery(String sqlQuery) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sqlQuery);
        } catch (SQLException sqlEx) {
            System.out.println("SQl Exception here : "+sqlEx.toString());
        }
        return rs;
    }
    
    public void processSQLStatement(String sqlQuery) throws SQLException{

    Statement st = null;
        try {
            st = connection.createStatement();
            st.execute(sqlQuery);
        } catch (SQLException sqlEx) {
            System.out.println("SQl Exception here insert: "+sqlEx.toString());
        }

    }
    
    public ResultSet processSQLStatementReturnGeneratedKey(String sqlQuery) throws SQLException{

    Statement st = null;
        try {
            st = connection.createStatement();
            st.execute(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            return st.getGeneratedKeys();
        } catch (SQLException sqlEx) {
            System.out.println("SQl Exception here insert: "+sqlEx.toString());
        }
        return null;
    }
}
