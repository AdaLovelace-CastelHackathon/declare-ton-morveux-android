package com.android.morveux.services;

import com.android.morveux.entities.School;

public class SchoolService {

    private School school;

    private static SchoolService INSTANCE;

    private SchoolService() {};

    public static SchoolService getInstance() {
        if(SchoolService.INSTANCE == null) {
            SchoolService.INSTANCE = new SchoolService();
        }
        return SchoolService.INSTANCE;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
