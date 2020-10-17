/*
 * PROJECT: PUBLIC LIBRARY CRUD.
 * LUIS J. BRAVO ZÚÑIGA.
 * MANAGER BOOK CLASS.
 */
package accessData;

import exception.MyException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import logicBusiness.Book;

public class ManagerBook {
    private static ManagerBook INSTANCE = null;

    private static final String INSERT_BOOK = "{call p_insert_book(?,?,?,?,?,?,?,?,?,?)}",
            UPDATE_BOOK = "{call p_update_book(?,?,?,?,?,?,?,?,?,?)}",
            DELETE_BOOK = "{call p_delete_book(?)}",
            CONSULT_BOOK = "{call p_consult_book(?)}",
            LIST_BOOK = "{call p_list_book()}";

    private ManagerBook() {
    }

    private Book book(ResultSet rs) {
        Book object = null;
        int i = 1;
        try {
            object = new Book(
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getInt(i++),
                    rs.getInt(i++)
            );
        } catch (SQLException ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return object;
    }

    public static ManagerBook getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerBook();
        }
        return INSTANCE;
    }

    public boolean insert(Book object) {
        boolean result = false;
        CallableStatement pstmt = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        try {
            pstmt = conexion.prepareCall(INSERT_BOOK);
            pstmt.clearParameters();
            pstmt.setString(1, object.getIsbn());
            pstmt.setString(2, object.getTitle());
            pstmt.setString(3, object.getFname());
            pstmt.setString(4, object.getLname());
            pstmt.setString(5, object.getEditorial());
            pstmt.setString(6, object.getYear());
            pstmt.setString(7, object.getIdiom());
            pstmt.setString(8, object.getPages());
            pstmt.setInt(9, object.getSubgenre());
            pstmt.setInt(10, object.getQuantity());
            if (pstmt.execute()) {
                throw new MyException(MyException.GET_ERROR_OPERATION());
            } else {
                result = true;
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
        return result;
    }

    public boolean update(Book object) {
        boolean result = false;
        CallableStatement pstmt = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        try {
            pstmt = conexion.prepareCall(UPDATE_BOOK);
            pstmt.clearParameters();
            pstmt.setString(1, object.getIsbn());
            pstmt.setString(2, object.getTitle());
            pstmt.setString(3, object.getFname());
            pstmt.setString(4, object.getLname());
            pstmt.setString(5, object.getEditorial());
            pstmt.setString(6, object.getYear());
            pstmt.setString(7, object.getIdiom());
            pstmt.setString(8, object.getPages());
            pstmt.setInt(9, object.getSubgenre());
            pstmt.setInt(10, object.getQuantity());
            if (pstmt.execute()) {
                throw new MyException(MyException.GET_ERROR_OPERATION());
            } else {
                result = true;
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
        return result;
    }

    public boolean delete(String isbn) {
        boolean result = false;
        CallableStatement pstmt = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        try {
            pstmt = conexion.prepareCall(DELETE_BOOK);
            pstmt.clearParameters();
            pstmt.setString(1, isbn);
            if (pstmt.execute()) {
                throw new MyException(MyException.GET_ERROR_OPERATION());
            } else {
                result = true;
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
        return result;
    }

    public Book consult(String IBSN) {
        Book result = null;
        ResultSet rs = null;
        CallableStatement pstmt = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        try {
            pstmt = conexion.prepareCall(CONSULT_BOOK);
            pstmt.clearParameters();
            pstmt.setString(1, IBSN);
            if ((rs = pstmt.executeQuery()) == null) {
                throw new MyException((MyException.GET_ERROR_OPERATION()));
            } else {
                if (rs.next()) {
                    result = book(rs);
                } else {
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
        return result;
    }

    public List<Book> list() {
        List<Book> list = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement pstmt = null;
        Connection conexion = ManagerConexionDB.getInstance().connect();
        try {
            pstmt = conexion.prepareCall(LIST_BOOK);
            pstmt.clearParameters();
             if ((rs = pstmt.executeQuery()) == null) {
                throw new MyException((MyException.GET_ERROR_OPERATION()));
            } else {

                while (rs.next()) {
                    list.add(book(rs));
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
   
} // END CLASS
