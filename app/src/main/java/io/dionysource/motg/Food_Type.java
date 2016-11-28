package io.dionysource.motg.usrclss;
import java.util.ArrayList;
/**
 * Created by Admin on 2016. 11. 20..
 */

public class Food_Type {

    String type;
    ArrayList<Food_subtype> food_sub;
    private Food_Type()
    {

    }


    public Food_Type(String type)
    {
        this.type = type;
        food_sub = null;
    }
    void subtype_add(String subtype) {
        int size = food_sub.size();
        Food_subtype sub = new Food_subtype(subtype);
        food_sub.add(size, sub);
    }
}
class Food_subtype
{
    String subtype;
    ArrayList<String> sub_sub;

    private Food_subtype()
    {

    }

    Food_subtype(String subtype)
    {
        this.subtype = subtype;
        sub_sub = null;
    }
    void subsubtype_add(String subsubtype) {
        int size = sub_sub.size();
        sub_sub.add(size, subsubtype);
    }
}