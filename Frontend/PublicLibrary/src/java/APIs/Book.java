/*
 * PROJECT: PUBLIC LIBRARY.
 * LUIS J. BRAVO ZUÑIGA.
 * BOOK API.
 */
package APIs;

import exception.MyException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Model;

@Path("book")
public class Book {
    
    //LIST
    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public List<logicBusiness.Book> list() {
        try {
            return Model.getInstance().listBook();
        } catch(Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return null;
    }
    
    //CONSULT
    @GET
    @Path("{isbn}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public logicBusiness.Book consult(@PathParam("isbn") String isbn) {
        try {
            return Model.getInstance().consultBook(isbn);
        } catch(Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return null;
    }
    
    //INSERT
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public boolean insert(logicBusiness.Book object) {
        try {
            return Model.getInstance().insertBook(object); 
        } catch(Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return false;
    }
    
    //UPDATE
    @PUT
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON) 
    public boolean update(logicBusiness.Book object) {
        try {
            return Model.getInstance().updateBook(object);
        } catch(Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return false;
    }
    
    //DELETE
    @DELETE
    @Path("{isbn}")
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("isbn") String isbn) {
        try {
            return Model.getInstance().deleteBook(isbn);
        } catch(Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return false;
    } 
    
}// END CLASS
