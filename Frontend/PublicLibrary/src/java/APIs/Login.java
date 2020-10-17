/*
 * PROJECT: PUBLIC LIBRARY.
 * LUIS J. BRAVO ZUÑIGA.
 * LOGIN API.
 */
package APIs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import logicBusiness.User;
import model.Model;
import validate.Check;

@Path("login")
public class Login {
    
    @Context
    HttpServletRequest request;
    
    @GET
    @Path("{username}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public User login(@PathParam("username") String username, @PathParam("password") String password) {
        if (Check.VALIDATE_EMAIL_ADDRESS(username) && Check.VALIDATE_PASSWORD(password)) {
            if (Model.getInstance().check(username, password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("username", username);
                Model.getInstance().setUsername(username);
                return new User(username, password);
            }
        }
        return null;
    }
    
} // END CLASS
