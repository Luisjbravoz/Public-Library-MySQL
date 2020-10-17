/*
 * PROJECT: PUBLIC LIBRARY CRUD.
 * LUIS J. BRAVO ZÚÑIGA.
 * CHECK CLASS.
 */
package validate;

import exception.MyException;

public class Check {

    public static boolean VALIDATE_EMAIL_ADDRESS(String data) {
        try {
            return data.matches("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        } catch (Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return false;
    }

    public static boolean VALIDATE_ONLY_NUMBERS(String data) {
        try {
            return data.matches("[0-9]*");
        } catch (Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return false;
    }

    public static boolean VALIDATE_ONLY_LETTERS(String data) {
        try {
            return data.matches("a-zA-Z");
        } catch (Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return false;
    }

    public static boolean VALIDATE_PASSWORD(String data) {
        try {
            return data.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
        } catch (Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return false;
    }
} //END CLASS
