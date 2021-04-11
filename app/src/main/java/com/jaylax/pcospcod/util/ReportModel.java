package com.jaylax.pcospcod.util;

public class ReportModel {

    public String patient_id;
    public String report_name;
    public String report;

    public ReportModel(String patient_id, String report_name, String report)
    {
        this.patient_id = patient_id;
        this.report_name = report_name;
        this.report = report;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public String getReport_name() {
        return report_name;
    }

    public String getReport() {
        return report;
    }
}
