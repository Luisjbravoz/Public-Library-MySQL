/*
 * PROJECT: PUBLIC LIBRARY CRUD.
 * LUIS J. BRAVO ZÚÑIGA.
 * MANAGER SUBGENRE CLASS.
 */
package accessData;

import exception.MyException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ManagerSubgenre {

    private static ManagerSubgenre INSTANCE = null;
    private static final String LIST_SUBGENRE = "{call p_list_subgenre()}";

    private ManagerSubgenre() {
    }

    public static ManagerSubgenre getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerSubgenre();
        }
        return INSTANCE;
    }

    public List<String> list() {
        List<String> list = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement pstmt = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        try {
            pstmt = conexion.prepareCall(LIST_SUBGENRE);
            pstmt.clearParameters();
            if ((rs = pstmt.executeQuery()) == null) {
                throw new MyException((MyException.GET_ERROR_OPERATION()));
            } else {
                while (rs.next()) {
                    list.add(rs.getString(1));
                }
                if (list.isEmpty()) {
                    throw new MyException(MyException.GET_ERROR_NO_DATA());
                }
            }
        } catch (SQLException | MyException ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                ManagerConexionDB.getInstance().disconnect();
            } catch (SQLException ex) {
                MyException.SHOW_ERROR(ex.getMessage());
            }
        }
        return list;
    }
    
} //END CLASS
