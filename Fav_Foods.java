package io.dionysource.motg.usrclss;

/**
 * Created by Admin on 2016. 11. 21..
 */

public class Fav_Foods extends Favorites{

    Food food;

    public Fav_Foods(Food food, String note)
    {
        super(note);
        this.food = food;
    }
}
