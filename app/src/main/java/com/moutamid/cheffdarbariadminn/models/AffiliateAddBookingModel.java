package com.moutamid.cheffdarbariadminn.models;

import java.util.ArrayList;

public class AffiliateAddBookingModel {
    public String name, id, occasion_type, number, party_venue_address,
            date_of_party, time, number_of_people, number_of_dishes, push_key;
    public ArrayList<String> cuisinesList = new ArrayList<>();

    public boolean booking_confirmed;

    public String affiliate_uid, affiliate_shop_name, affiliate_number, affiliate_city,
            affiliate_shop_address, time_stamp;

    public AffiliateAddBookingModel() {
    }
}
