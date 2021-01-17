package facades;

import DTOs.HotelDTO;
import com.google.gson.JsonObject;
import errorhandling.exceptions.FetchException;
import handlers.HotelFetchHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Mathias
 */
public class HotelFacade {
    
    private static HotelFacade instance;
    
    private HotelFacade(){
        // Private constructor to ensure Singleton
    }
    
    public static HotelFacade getHotelFacade(){
        if (instance == null) {
            instance = new HotelFacade();
        }

        return instance;
    }
    
    public List<HotelDTO> getAllHotels(ExecutorService threadPool) throws FetchException {
        List<HotelDTO> hotelDTOs = new ArrayList<>();
        HotelFetchHandler handler = new HotelFetchHandler("id", "name", "address",
                "phone", "http://exam.cphdat.dk:8000/hotel/all");
        
        List<Future<JsonObject>> futures = new ArrayList<>();
        Future<JsonObject> futureJson = threadPool.submit(handler);
        futures.add(futureJson);
        
        for (Future<JsonObject> future : futures) {
            JsonObject jsonObject = new JsonObject();
            hotelDTOs.add(convertToHotelDTO(jsonObject));
        }
        
        return hotelDTOs;
    }
    
    public HotelDTO getHotelById(int id){
        return null;
    }
    
    private HotelDTO convertToHotelDTO(JsonObject jsonObject) throws FetchException{
        String idValue, name, address, phone;
        int id;
        
        try {
            idValue = jsonObject.get("id").getAsString();
            name = jsonObject.get("name").getAsString();
            address = jsonObject.get("address").getAsString();
            phone = jsonObject.get("phone").getAsString();
            id = jsonObject.get(idValue).getAsInt();
        }
        catch (NullPointerException e) {
            throw new FetchException("A system error occurred while converting one or more jokes from our partners, please contact us regarding the error.", 500);
        }
        
        
        return new HotelDTO(id, name, address, phone);
    }

}
