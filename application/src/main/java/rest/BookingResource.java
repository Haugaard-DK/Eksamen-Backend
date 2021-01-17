package rest;

import DTOs.BookingDTO;
import DTOs.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import errorhandling.exceptions.API_Exception;
import errorhandling.exceptions.DatabaseException;
import errorhandling.exceptions.UserCreationException;
import facades.BookingFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import static rest.AuthenticationResource.USER_FACADE;
import utils.EMF_Creator;

/**
 *
 * @author Mathias
 */
@Path("Booking")
public class BookingResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BookingFacade FACADE = BookingFacade.getBookingFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("getAll")
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
        List<BookingDTO> bookings = FACADE.getAllBookings();
        return Response.ok(bookings).build();
    }

    @POST
    @Path("bookHotel")
    @RolesAllowed("User")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookHotel(String jsonString) throws API_Exception, DatabaseException {
        String startDate, numberOfNights, pricePerNight;

        /*startDate = getStringFromJson("startDate", jsonString);
        numberOfNights = getStringFromJson("numberOfNights", jsonString);
        pricePerNight = getStringFromJson("pricePerNight", jsonString);

        BookingDTO booking = FACADE.createBooking(startDate, numberOfNights, pricePerNight);

        return Response.ok(booking).build();
    */
        JsonObject jsonObject = new JsonObject();

        // Extracts user credentials
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            startDate = json.get("startDate").getAsString();
            numberOfNights = json.get("numberOfNights").getAsString();
            pricePerNight = json.get("pricePerNight").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        
        try {
            BookingDTO booking = FACADE.createBooking(startDate, numberOfNights, pricePerNight);

            // Preparing respone
            jsonObject.addProperty("startDate", booking.getStartDate());
            jsonObject.addProperty("numberOfNights", booking.getStartDate());
            jsonObject.addProperty("pricePerNight", booking.getStartDate());

            return Response.ok(GSON.toJson(jsonObject)).build();
        } catch (Exception e) {
            if (e instanceof DatabaseException) {
                throw (DatabaseException) e;
            }

            throw new API_Exception("Something went wrong, please try again later ...");
        }
    }

    private int getIntFromJson(String keyword, String jsonString) throws API_Exception {
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            return (json.get(keyword).getAsInt());
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
    }

    private String getStringFromJson(String keyword, String jsonString) throws API_Exception {
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            return (json.get(keyword).getAsString());
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
    }

}
