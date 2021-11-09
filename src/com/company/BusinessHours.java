package com.company;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BusinessHours {
   private LinkedHashMap<DayOfWeek, LocalTime> openingHours;

    public BusinessHours() {
        openingHours = new LinkedHashMap<>();
        EnumSet<DayOfWeek> daysOfWeek = EnumSet.allOf( DayOfWeek.class );  // Collect all seven day-of-week objects, in order Monday-Sunday.
        for( DayOfWeek dow : daysOfWeek ) {
            if(!dow.equals(DayOfWeek.MONDAY)) {
                if(dow.getValue() % 2 == 0) {
                    openingHours.put(dow, LocalTime.of(12, 0));
                } else {
                    openingHours.put(dow, LocalTime.of(11, 0));
                }
            }
        }
    }

    public boolean isOpenNow() {
        LocalDateTime now = LocalDateTime.now();
        if(!openingHours.containsKey(now.getDayOfWeek())) {
            return false;
        } else {
            if(now.getHour() > this.getClosingTime(openingHours.get(now.getDayOfWeek())).getHour() || now.getHour() < openingHours.get(now.getDayOfWeek()).getHour() ) {
              return false;
            }
       }
        return true;
    }

    private LocalTime getClosingTime(LocalTime openTime) {
        return (openTime.plus(12, ChronoUnit.HOURS));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (Map.Entry<DayOfWeek, LocalTime> entry : openingHours.entrySet()) {
           sb.append(entry.getKey()).append(" ");
           sb.append(entry.getValue()).append(" - ").append(this.getClosingTime(entry.getValue())).append("\n");
        }
        return "Opening hours are: " + sb;
    }

}
