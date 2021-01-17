package DTOs;

import entities.Booking;

/**
 *
 * @author Mathias
 */
public class BookingDTO {
    
    private String startDate;
    private String numberOfNights;
    private String pricePerNight;

    public BookingDTO() {
    }
    
    public BookingDTO(Booking booking) {
        this.startDate = booking.getStartDate();
        this.numberOfNights = booking.getNumberOfNights();
        this.pricePerNight = booking.getPricePerNight();
    }

    public String getStartDate() {
        return startDate;
    }

    public String getNumberOfNights() {
        return numberOfNights;
    }

    public String getPricePerNight() {
        return pricePerNight;
    }
    
    

}
