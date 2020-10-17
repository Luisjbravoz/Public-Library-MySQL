/*
 * PROJECT: PUBLIC LIBRARY CRUD.
 * LUIS J. BRAVO ZÚÑIGA.
 * FORMAT CLASS.
 */
package format;

import java.util.List;
import logicBusiness.Book;

public class Format {

    private static final String FORMAT_OUTPUT_USER = "USERNAME: %s%nPASSWORD: %s%n";

    private static final String FORMAT_OUTPUT_BOOK = "ISBN: %s%nTITLE: %s%nAUTHOR: %s %s%nYEAR: %s%n"
            + "EDITORIAL: %s%nIDIOM: %s%nPAGES: %s%n"
            + "ID SUBGENRE: %d%nQUANTITY: %d%n";

    private static final String FORMAT_OUTPUT_LIST = "[%d]%n%s";
    
    private static final String NO_DATA_MESSAGE = "No data for show!";

    public static String GET_FORMAT_OUTPUT_USER() {
        return FORMAT_OUTPUT_USER;
    }

    public static String GET_FORMAT_OUTPUT_BOOK() {
        return FORMAT_OUTPUT_BOOK;
    }

    public static final String FORMAT_LIST_BOOK(List<Book> list) {
        StringBuilder l = new StringBuilder();
        if (list.isEmpty()) {
            l.append(NO_DATA_MESSAGE);
        } else {
            int i = 0;
            for (Book item : list) {
                l.append(String.format(FORMAT_OUTPUT_LIST, ++i, item.toString()));
            }
        }
        return l.toString();
    }

} //END CLASS
