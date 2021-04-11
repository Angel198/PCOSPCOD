package com.jaylax.pcospcod.util;

public class OngoingPatientDetail {

    public String name;
    public String id;
    public String treatment_id;
    public String treatment_date;
    public String treatment_status;

    public OngoingPatientDetail(String id, String name, String treatment_id, String treatment_date, String treatment_status)
    {
        this.id = id;
        this.name = name;
        this.treatment_id = treatment_id;
        this.treatment_date = treatment_date;
        this.treatment_status = treatment_status;

    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getTreatment_id() {
        return treatment_id;
    }

    public String getTreatment_date() {
        return treatment_date;
    }

    public String getTreatment_status() {
        return treatment_status;
    }
}

