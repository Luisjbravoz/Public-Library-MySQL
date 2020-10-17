/*
 * PROJECT: PUBLIC LIBRARY CRUD.
 * LUIS J. BRAVO ZÚÑIGA.
 * MODEL CLASS.
 */

package model;

import accessData.ManagerBook;
import accessData.ManagerSubgenre;
import accessData.ManagerUser;
import java.util.List;
import logicBusiness.Book;

public class Model {
    
    private static Model INSTANCE = null;
    private String username;
    
    private Model() {
        username = null;
    }
    
    public static Model getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public boolean check(String username, String password) {
        return ManagerUser.getInstance().check(username, password);
    }
    
    public boolean updateUser(String username, String password) {
        return ManagerUser.getInstance().update(username, password);
    }
    
    public boolean insertBook(Book object) {
        return ManagerBook.getInstance().insert(object);
    }
    
    public boolean updateBook(Book object) {
        return ManagerBook.getInstance().update(object);
    }
    
    public boolean deleteBook(String isbn) {
        return ManagerBook.getInstance().delete(isbn);
    }
    
    public Book consultBook(String isbn) {
        return ManagerBook.getInstance().consult(isbn);
    }
    
    public List<Book> listBook() {
        return ManagerBook.getInstance().list();
    }
    
    public List<String> listSubgenre() {
        return ManagerSubgenre.getInstance().list();
    }
    
} // END CLASS