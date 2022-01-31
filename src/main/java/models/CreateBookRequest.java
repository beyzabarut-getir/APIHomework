package models;

import java.util.Map;

public class CreateBookRequest{
    public String firstname;
    public String lastname;
    public Number totalprice;
    public Boolean depositpaid;
    public Map<String, String> bookingdates;
    public String additionalneeds;

    public CreateBookRequest(String firstname, String lastname, Number totalprice, Boolean depositpaid, Map<String, String> bookingdates, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

}
