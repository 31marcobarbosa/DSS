/*
 * Connect
 *
 */
package homesplit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Grupo 35
 */
public class Connect {
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "localhost";
    private static final String SCHEMA = "mydb";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     /**
     * Abre a ligação à base de dados.
     * @return Connection 
     * @throws java.sql.SQLException 
     */
    
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://"+URL+"/"+SCHEMA+"?autoReconnect=true&useSSL=false",USERNAME,PASSWORD);
    }
    
    /**
     * Fecha a ligação à base de dados, se aberta.
     * @param c Connection 
     */
    public static void close(Connection c) {
        try {
            if(c!=null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
