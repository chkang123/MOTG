package io.dionysource.motg.usrclss;

/**
 * Created by Admin on 2016. 11. 26..
 */

public class Fav_Rstrnts extends Favorites {

    String restaurant; // 맛집의 id

    public Fav_Rstrnts(String restaurant, String note)
    {
        super(note);
        this.restaurant = restaurant;
    }
}
