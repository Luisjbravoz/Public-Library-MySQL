/*
 * PROJECT: PUBLIC LIBRARY
 * LUIS J. BRAVO ZÚÑIGA.
 * MANAGER CONEXION BD CLASS.
 */
package accessData;

import exception.MyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManagerConexionDB {

    private static ManagerConexionDB INSTANCE = null;
    private Connection conexion;

    private ManagerConexionDB() {
        conexion = null;
    }

    public static ManagerConexionDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerConexionDB();
        }
        return INSTANCE;
    }

    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/public_library", "root", "root");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return conexion;
    }

    public void disconnect() {
        try {
            if(!conexion.isClosed()) {
                conexion.close();
            } 
        } catch(SQLException ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
    }

} // END CLASS
