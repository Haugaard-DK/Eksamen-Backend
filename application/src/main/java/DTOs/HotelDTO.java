package DTOs;

/**
 *
 * @author Mathias
 */
public class HotelDTO {
    
    private int id;
    private String name;
    private String address;
    private String phone;

    public HotelDTO(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

}
