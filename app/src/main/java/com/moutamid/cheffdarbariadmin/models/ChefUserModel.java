package com.moutamid.cheffdarbariadmin.models;

import java.util.ArrayList;

public class ChefUserModel {
    public String name, number, whatsapp, email, password, city, home_town,
    language_speak, age, highest_education, post, total_experience, has_vehicle,
    monthly_salary, physical_status, work_1_hotel_name, work_1_post, work_1_city,
            work_1_worked_years, work_1_certificate, work_2_hotel_name, work_2_post,
            work_2_city, work_2_worked_years, work_2_certificate, professional_photo_url,
    aadhaar_card_url, education_certificate_url, resume_url, covid_vaccination_url;
    public ArrayList<String> expertInList = new ArrayList<>();

    public ChefUserModel() {
    }
}
