package org.application.models;

import java.util.HashMap;
import java.util.Map;

public enum DayOfWeek {

    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота");
    private String name;

    DayOfWeek(String name) {
        this.name = name;
    }

    public static DayOfWeek getDayByName(String name){
        if (name.equals("Понедельник")){
            return DayOfWeek.MONDAY;
        } else if (name.equals("Вторник")) {
            return DayOfWeek.TUESDAY;
        } else if (name.equals("Среда")) {
            return DayOfWeek.WEDNESDAY;
        } else if (name.equals("Четверг")) {
            return DayOfWeek.THURSDAY;
        } else if (name.equals("Пятница")) {
            return DayOfWeek.FRIDAY;
        } else if (name.equals("Суббота")) {
            return DayOfWeek.SATURDAY;
        }
        else{
            return null;
        }
    }

    public String getName(){
        return name;
    }

}
