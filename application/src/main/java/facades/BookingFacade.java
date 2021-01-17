package facades;

import DTOs.BookingDTO;
import entities.Booking;
import errorhandling.exceptions.DatabaseException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Mathias
 */
public class BookingFacade {
    
    private static EntityManagerFactory emf;
    private static BookingFacade instance;
    
    private BookingFacade(){
        
    }
    
    public static BookingFacade getBookingFacade(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new BookingFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public BookingDTO createBooking(String startDate, String numberOfNights, String pricePerNight) throws DatabaseException{
        
        EntityManager em = getEntityManager();
        
        Booking booking = new Booking(startDate, numberOfNights, pricePerNight);
        
        try{
            em.getTransaction().begin();
            em.persist(booking);
            em.getTransaction().commit();
            
            return new BookingDTO(booking);
        }
        catch (Exception e){
             throw new DatabaseException("Something went wrong! Failed to create booking, please try again later.");
        }
        finally {
            em.close();
        }
        
    }
    
    public List<BookingDTO> getAllBookings(){
        EntityManager em = getEntityManager();
        
        List<Booking> bookings;
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        
        try {
            Query query = em.createNamedQuery("Booking.getAll");
            bookings = query.getResultList();
            
            bookings.forEach(booking -> {
                bookingDTOs.add(new BookingDTO(booking));
            });
            
            return bookingDTOs;
        }
        finally{
            em.close();
        }
    }
}
