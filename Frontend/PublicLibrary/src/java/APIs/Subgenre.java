/*
 * PROJECT: PUBLIC LIBRARY.
 * LUIS J. BRAVO ZUÑIGA.
 * SUBGENRE API.
 */
package APIs;

import com.google.gson.Gson;
import exception.MyException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import model.Model;

@Path("subgenre")
public class Subgenre {
    
    //LIST
    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String list() {
        try {
            return new Gson().toJson(Model.getInstance().listSubgenre());
        } catch(Exception ex) {
            MyException.SHOW_ERROR(ex.getMessage());
        }
        return null;
    }
}
