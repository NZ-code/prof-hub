package org.example.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    private String name;
    private Date startDate;
    private List<Lecture> lectures = new ArrayList<Lecture>();
    private Domain domain;

}
