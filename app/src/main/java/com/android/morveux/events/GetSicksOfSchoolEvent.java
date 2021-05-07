package com.android.morveux.events;

import com.android.morveux.entities.School;

public class GetSicksOfSchoolEvent {

    public School school;

    public GetSicksOfSchoolEvent(School school) {
        this.school = school;
    }
}
