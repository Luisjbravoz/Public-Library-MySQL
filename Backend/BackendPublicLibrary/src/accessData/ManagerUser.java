/*
 * PROJECT: PUBLIC LIBRARY
 * LUIS J. BRAVO ZÚÑIGA.
 * MANAGER USER CLASS.
 */
package accessData;

import exception.MyException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ManagerUser {

    private static ManagerUser INSTANCE = null;
    private static final String LOGIN = "{?= call f_check_login(?,?)}",
            UPDATE_USER = "{call p_update_user(?,?)}";

    private ManagerUser() {

    }

    public static ManagerUser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerUser();
        }
        return INSTANCE;
    }

    public boolean check(String username, String password) {
        CallableStatement pstm = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        short result = -1;
        try {
            pstm = conexion.prepareCall(LOGIN);
            pstm.clearParameters();
            pstm.registerOutParameter(1, Types.INTEGER);
            pstm.setString(2, username);
            pstm.setString(3, password);
            if (pstm.execute()) {
                throw new MyException(MyException.GET_ERROR_OPERATION());
            } else {
                result = (short) pstm.getBigDecimal(1).intValue();
            }
        } catch (SQLException | MyException ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                ManagerConexionDB.getInstance().disconnect();
            } catch (SQLException ex) {
                MyException.SHOW_ERROR(ex.getMessage());
            }
        }
        return result == 1;
    }

    public boolean update(String username, String password) {
        CallableStatement pstm = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        boolean result = false;
        try {
            pstm = conexion.prepareCall(UPDATE_USER);
            pstm.clearParameters();
            pstm.setString(1, username);
            pstm.setString(2, password);
            if (pstm.execute()) {
                throw new MyException(MyException.GET_ERROR_OPERATION());
            } else {
                result = true;
            }
        } catch (SQLException | MyException ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                ManagerConexionDB.getInstance().disconnect();
            } catch (SQLException ex) {
                MyException.SHOW_ERROR(ex.getMessage());
            }
        }
        return result;
    }

} // END CLASS
