package com.company;

import com.company.users.Observer;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BusinessHours implements Observable {
   private LinkedHashMap<DayOfWeek, LocalTime> openingHours;
   private List<Observer> users = new ArrayList<>();
   private String msg;
   private boolean isOpen = false;

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
            isOpen = false;
            notifyObservers("Sorry, the restaurant is closed right now - you won't be able to place any orders.");
        } else if(now.getHour() > this.getClosingTime(openingHours.get(now.getDayOfWeek())).getHour() || now.getHour() < openingHours.get(now.getDayOfWeek()).getHour()) {
              isOpen = false;
            notifyObservers("Sorry, the restaurant is closed right now - you won't be able to place any orders.");
       } else {
            isOpen = true;
            notifyObservers("The restaurant is currently open.");
        }
        return isOpen;
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

    @Override
    public void registerObserver(Observer o) {
        users.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        users.remove(o);
    }

    @Override
    public void notifyObservers(String updates) {
        for(Observer user: users) {
            user.update(updates);
        }
    }
}
