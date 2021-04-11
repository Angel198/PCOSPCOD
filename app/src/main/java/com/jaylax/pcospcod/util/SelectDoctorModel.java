package com.jaylax.pcospcod.util;

public class SelectDoctorModel {

    public String id;
    public String Name;
    public String specialization;
    public String image;

    public SelectDoctorModel(String id, String Name , String specialization, String image)
    {
        this.id = id;
        this.Name = Name;
        this.specialization = specialization;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }

    public String getSpecialization() {
        return specialization;
    }
}

