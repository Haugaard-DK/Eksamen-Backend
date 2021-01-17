package handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.concurrent.Callable;
import utils.HttpUtils;

/**
 *
 * @author Mathias
 */
public class HotelFetchHandler implements Callable<JsonObject>{
    
    private String id;
    private String name;
    private String address;
    private String phone;
    private String url;

    public HotelFetchHandler(String id, String name, String address, String phone, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.url = url;
    }

    @Override
    public JsonObject call() throws Exception {
        String fetchedData = HttpUtils.fetchData(url);
        return convertToJSON(fetchedData);
    }

    private JsonObject convertToJSON(String fetchedData) {
        JsonObject jsonObject = JsonParser.parseString(fetchedData).getAsJsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("address", address);
        jsonObject.addProperty("phone", phone);
        
        return jsonObject;
    }

    @Override
    public String toString() {
        return "HotelFetchHandler{" + "id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", url=" + url + '}';
    }
    
    

}
