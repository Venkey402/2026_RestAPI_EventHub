package org.eventHub.com.StepDefs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.eventHub.com.POJOs.BookingDetails;
import org.eventHub.com.POJOs.EventDetails;
import org.eventHub.com.POJOs.UserDetails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseClass {
    public String email;
    public String pass;
    public String token;
    public String userid;
    UserDetails userDetails;
    int eventId;
    RequestSpecification requestSpec;
    int bookingId;

    public void createEmailPassword()
    {
        email = RandomStringUtils.randomAlphabetic(3)+ RandomStringUtils.randomAlphanumeric(6)+"@"+RandomStringUtils.randomAlphabetic(5)+".com";
        System.out.println(email);
        pass=RandomStringUtils.randomAlphanumeric(7)+"@";
        System.out.println(pass);
    }

    public UserDetails createUserDetailsPojo()
    {
        createEmailPassword();
        userDetails = new UserDetails();
        userDetails.setEmail(email);
        userDetails.setPassword(pass);
        return userDetails;
    }



    public EventDetails getEventDetails() {
        EventDetails eventDetails = new EventDetails();
        eventDetails.setTitle("Tech Summit 2026-April");
        eventDetails.setDescription("A premier technology conference.");
        eventDetails.setCategory("Conference");
        eventDetails.setVenue("Hyderabad Hi-tech city");
        eventDetails.setCity("Hyderabad");
        eventDetails.setEventDate("2026-06-15T09:00:00.000Z");
        eventDetails.setPrice(300);
        eventDetails.setTotalSeats(1000);
        eventDetails.setImageUrl("");
        return eventDetails;
    }

    public EventDetails udpatedEventDetails() {
        EventDetails eventDetails = new EventDetails();
        eventDetails.setTitle("Tech Summit 2026-April");
        eventDetails.setDescription("A premier technology conference.");
        eventDetails.setCategory("Conference");
        eventDetails.setVenue("Hyderabad Hi-tech city");
        eventDetails.setCity("Hyderabad");
        eventDetails.setEventDate("2026-06-15T09:00:00.000Z");
        eventDetails.setPrice(600);
        eventDetails.setTotalSeats(1000);
        eventDetails.setImageUrl("");
        return eventDetails;
    }

    public BookingDetails setBookingDetails()
    {
        BookingDetails bookingDetails= new BookingDetails();
        bookingDetails.setEventId(eventId);
        bookingDetails.setCustomerName("abc");
        bookingDetails.setCustomerEmail("a@b.com");
        bookingDetails.setCustomerPhone("1234567890");
        bookingDetails.setQuantity(9);
        return bookingDetails;
    }

}
