package com.jaylax.pcospcod.util;

public class PatientInquiryModel {

    public String patient_id;
    public String patient_name;
    public String patient_contact;
    public String patient_age;
    public String image;

    public PatientInquiryModel(String patient_id, String patient_name, String patient_contact, String patient_age, String image)
    {
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.patient_contact = patient_contact;
        this.patient_age = patient_age;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public String getPatient_contact() {
        return patient_contact;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

}
