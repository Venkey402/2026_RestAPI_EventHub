package org.eventHub.com.TestContext;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.eventHub.com.POJOs.BookingDetails;
import org.eventHub.com.POJOs.EventDetails;
import org.eventHub.com.POJOs.UserDetails;

public class TestContext {

    public String email;
    public String pass;
    public String token;
    public String userid;
    public int eventId;
    public UserDetails userDetails;
    public BookingDetails bookingDetails;
    public RequestSpecification requestSpec;
    public int bookingId;

    public TestContext()
    {
        requestSpec = new RequestSpecBuilder().setBaseUri("https://api.eventhub.rahulshettyacademy.com/api").setContentType("application/json").build();
    }

    public void createEmailPassword()
    {
        email = RandomStringUtils.randomAlphabetic(3)+ RandomStringUtils.randomAlphanumeric(6)+"@"+RandomStringUtils.randomAlphabetic(5)+".com";
        System.out.println(email);
        pass=RandomStringUtils.randomAlphanumeric(7)+"@";
        System.out.println(pass);
    }

    public UserDetails createUserDetailsPojo()
    {
        if(userDetails==null) {
            createEmailPassword();
            userDetails = new UserDetails();
            userDetails.setEmail(email);
            userDetails.setPassword(pass);
        }
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
        bookingDetails= new BookingDetails();
        bookingDetails.setEventId(eventId);
        bookingDetails.setCustomerName("abc");
        bookingDetails.setCustomerEmail("a@b.com");
        bookingDetails.setCustomerPhone("1234567890");
        bookingDetails.setQuantity(9);
        return bookingDetails;
    }
}
