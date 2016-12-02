package io.dionysource.motg.usrclss;

/**
 * Created by Admin on 2016. 11. 21..
 */

public class Fav_Foods extends Favorites{

    String food;

    public Fav_Foods(String food, String note)
    {
        super(note);
        this.food = food;
    }
}
