/*
 * PROJECT: PUBLIC LIBRARY CRUD.
 * LUIS J. BRAVO ZÚÑIGA.
 * BOOK CLASS.
*/
package logicBusiness;

import exception.MyException;
import format.Format;
import java.util.Objects;

public class Book {
   
    private String isbn;
    private String title;
    private String fname;
    private String lname;
    private String editorial;
    private String year;
    private String idiom;
    private String pages;
    private int subgenre;
    private int quantity;

    public Book(String isbn, String title, String fname, String lname, 
            String editorial, String year, String idiom, String pages, int subgenre, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.fname = fname;
        this.lname = lname;
        this.editorial = editorial;
        this.year = year;
        this.idiom = idiom;
        this.pages = pages;
        this.subgenre = subgenre;
        this.quantity = quantity;
    }

    public Book() {
        this.isbn = this.title = this.fname = this.lname = this.editorial = 
                this.year = this.idiom = this.pages = null;
        this.subgenre = this.quantity = -1;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getYear() {
        return year;
    }

    public String getIdiom() {
        return idiom;
    }

    public String getPages() {
        return pages;
    }

    public int getSubgenre() {
        return subgenre;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setSubgenre(int subgenre) {
        this.subgenre = subgenre;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(Format.GET_FORMAT_OUTPUT_BOOK(), 
                             getIsbn(), getTitle(), getLname(), getFname(),
                             getEditorial(), getYear(), getIdiom(), getPages(),
                             getSubgenre(), getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.isbn)
        + Objects.hashCode(this.title)
        + Objects.hashCode(this.fname)
        + Objects.hashCode(this.lname)
        + Objects.hashCode(this.editorial)
        + Objects.hashCode(this.year)
        + Objects.hashCode(this.idiom)
        + Objects.hashCode(this.pages)
        + this.subgenre
        + this.quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Book object = (Book) obj;
        return this.hashCode() == object.hashCode();
    }

    @Override
    protected Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return obj;
    }

    public Book getClone() {
        return (Book) this.clone();
    }

} //END CLASS
