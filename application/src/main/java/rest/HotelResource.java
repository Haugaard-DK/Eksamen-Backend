package rest;

import DTOs.HotelDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.exceptions.FetchException;
import facades.HotelFacade;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Mathias
 */
@Path("hotel")
public class HotelResource {
    
    private static final HotelFacade FACADE = HotelFacade.getHotelFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ExecutorService THREADPOOL = Executors.newCachedThreadPool();
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHotelById(@PathParam("id") int hotelId){
        HotelDTO hotelDTO = FACADE.getHotelById(hotelId);
        return Response.ok(hotelDTO).build();
    }
    
    @GET
    @Path("allHotels")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHotels() throws FetchException{
        List<HotelDTO> hotels = FACADE.getAllHotels(THREADPOOL);
        return Response.ok(hotels).build();
    }
    
    

}
