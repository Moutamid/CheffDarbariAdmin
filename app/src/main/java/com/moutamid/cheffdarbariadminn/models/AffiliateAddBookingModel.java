package com.moutamid.cheffdarbariadminn.models;

import java.util.ArrayList;

public class AffiliateAddBookingModel {
    public String name, id, occasion_type, number, party_venue_address,
            date_of_party, time, number_of_people, number_of_dishes;
    public ArrayList<String> cuisinesList = new ArrayList<>();

    public boolean booking_confirmed;

    public AffiliateAddBookingModel() {
    }
}
