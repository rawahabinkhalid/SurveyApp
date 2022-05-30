package com.matzsolutions.surveyapp.Retailor;

public class OrganizationsModel {
    private String OrganizationID;
    private String Name;

    public OrganizationsModel(String organizationID, String name) {
        OrganizationID = organizationID;
        Name = name;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(String organizationID) {
        OrganizationID = organizationID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
