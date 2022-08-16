package com.moutamid.cheffdarbariadmin.models;

import java.util.ArrayList;

public class JobsAdminModel {
    public String occasion_type, name, number, party_address, location, city,
    number_of_people, date, time, no_of_dishes, dish_type, staff_required,
    payment, number_of_gas_burners, breakfast_items, lunch_items, dinner_items,
            snack_items;
    public ArrayList<String> kitchen_tools_list = new ArrayList<>();
    public ArrayList<String> cuisines_list = new ArrayList<>();

    public JobsAdminModel() {
    }
}
