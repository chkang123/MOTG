package io.dionysource.motg.usrclss;
import java.util.ArrayList;
/**
 * Created by Admin on 2016. 11. 20..
 */

public class Food_Type {

    ArrayList<Food_subtype> food_sub;

    public Food_Type()
    {
        food_sub = null;
    }
    void type_add(String subtype)
    {

    }
}

class Food_subtype
{
    String type;
    ArrayList<Food_subsubtype> sub_sub;

    Food_subtype(String subtype){
        this.type = subtype;
        sub_sub = null;
    }
    void type_add(String subsubtype) {
        int size = sub_sub.size();
        Food_subsubtype food_subsubtype = new Food_subsubtype(subsubtype);
        sub_sub.add(size, food_subsubtype);
    }
}

class Food_subsubtype
{
    String subtype;
    ArrayList<String> sub_sub;

    Food_subsubtype(String subtype){
        this.subtype = subtype;
        sub_sub = null;
    }
    void type_add(String subsubtype) {
        int size = sub_sub.size();
        sub_sub.add(size, subsubtype);
    }
    void type_delete(String subsubtype){
        if(sub_sub.contains(subsubtype)) {
            sub_sub.remove(subsubtype);
        }
    }
}