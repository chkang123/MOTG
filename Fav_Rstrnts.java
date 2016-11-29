package io.dionysource.motg.usrclss;

/**
 * Created by Admin on 2016. 11. 26..
 */

public class Fav_Rstrnts extends Favorites {

    Restaurant restaurant;

    public Fav_Rstrnts(Restaurant restaurant, String note)
    {
        super(note);
        this.restaurant = restaurant;
    }
}
