package io.dionysource.motg.usrclss;

/**
 * Created by Admin on 2016. 11. 21..
 */

class Favorites {
    int year;
    int month;
    int day;
    int hour;
    String note; // 간단한 메모 같은 거.

    public Favorites(String note)
    {
        this.note = note;
    }

    public void setdate(int year, int month, int day, int hour)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }
}


