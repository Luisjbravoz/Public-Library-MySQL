/*
 * PROJECT: PUBLIC LIBRARY CRUD.
 * LUIS J. BRAVO ZÚÑIGA.
 * MYEXCEPTION CLASS.
 */
package exception;

public class MyException extends Exception {

    private static final String FORMAT_OUTPUT_EXP = "ERROR: %S!%n",
            ERROR_OPERATION = "An error occurred, the operation was not completed",
            ERROR_NO_DATA = "No data found";

    public MyException(String message) {
        super(message);
    }

    public static void SHOW_ERROR(String message) {
        System.err.println(String.format(FORMAT_OUTPUT_EXP, message));
    }

    public static String GET_ERROR_OPERATION() {
        return ERROR_OPERATION;
    }

    public static String GET_ERROR_NO_DATA() {
        return ERROR_NO_DATA;
    }
    
    
    
    
} //END CLASS
