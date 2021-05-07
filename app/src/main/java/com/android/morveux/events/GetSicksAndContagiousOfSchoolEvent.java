package com.android.morveux.events;

import com.android.morveux.entities.School;

public class GetSicksAndContagiousOfSchoolEvent {

    public School school;

    public GetSicksAndContagiousOfSchoolEvent(School school) {
        this.school = school;
    }
}
